package ssafy.StackFlow.Domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.product.entity.Color;
import ssafy.StackFlow.Domain.product.entity.Product;
import ssafy.StackFlow.Domain.product.entity.ProductStore;
import ssafy.StackFlow.Domain.product.entity.Size;
import ssafy.StackFlow.Domain.store.entity.Store;
import ssafy.StackFlow.Domain.category.entity.Category;
import ssafy.StackFlow.Domain.category.entity.CategoryGroup;
import ssafy.StackFlow.Domain.product.repository.ColorRepository;
import ssafy.StackFlow.Domain.product.repository.ProductRepo;
import ssafy.StackFlow.Domain.product.repository.ProductStoreRepository;
import ssafy.StackFlow.Domain.product.repository.SizeRepository;
import ssafy.StackFlow.Domain.store.repository.StoreRepository;
import ssafy.StackFlow.Domain.category.repository.CategoryGroupRepository;
import ssafy.StackFlow.Domain.category.repository.CategoryRepository;
import ssafy.StackFlow.Domain.product.dto.ProductUpdateDto;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    private final CategoryGroupRepository categoryGroupRepository;
    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
//    private final BrandRepository brandRepository;
    private final StoreRepository storeRepository;
    private final ProductStoreRepository productStoreRepository;

    @Transactional
    public void saveProduct(Product product) {
        productRepo.save(product);
    }
    public int getStockQuantity(Long storeId, Long productId) {
        ProductStore productStore = productStoreRepository.findByStoreIdAndProductId(storeId, productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Store와 Product의 관계를 찾을 수 없습니다."));
        return productStore.getStockQuantity();
    }
    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    public Product findProductById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 상품이 없습니다."));
    }
    public List<Product> search(String keyword) {
        return productRepo.findByProdCodeContaining(keyword);
    }

    public List<Product> searchByFilters(String categoryGroup, String categoryCode, String colorCode, String size) {
        return productRepo.findProductsByFilters(categoryGroup, categoryCode, colorCode, size);
    }
    public List<Product> searchProducts(String keyword) {
        return productRepo.findByProdCodeContaining(keyword);
    }

    @Transactional
//    public void create(String prodName, String prodDetail, String prodCode, String brandCode, String categorygroup, String categoryCode, String colorCode, String size,
//                       int stockPrice, int stockQuantity, int sellPrice)
    public Product create(String prodName, String prodDetail, String prodCode, String categorygroup, String categoryCode, String colorCode, String size,
                       int stockPrice, int stockQuantity, int sellPrice)
    {
        Category category = categoryRepository.findByCateCode(categoryCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리 없음."));
        CategoryGroup categoryGroup = categoryGroupRepository.findByGroupName(categorygroup)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리 그룹 없음"));
        Color color = colorRepository.findByColorCode(colorCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리 없음."));
        Size productSize = sizeRepository.findBySize(size)
                .orElseThrow(() -> new IllegalArgumentException("해당 사이즈가 없음."));
//        Brand brand = brandRepository.findByBrandCode(brandCode)
//                .orElseThrow(() -> new IllegalArgumentException("해당 브랜드 없음"));
        Store store = storeRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("해당 매장 없음."));

        Product p = new Product();
        p.setProdName(prodName);
        p.setProdCode(prodCode);
//        p.setBrandCode(brand);
        p.setStockPrice(stockPrice);
//        p.setStockQuantity(stockQuantity);
        p.setSellPrice(sellPrice);
        p.setProdCate(category);
        p.setColorCode(color);
        p.setSize(productSize);
        p.setCateGroup(categoryGroup);
        p.setProdDetail(prodDetail);

        ProductStore productStore = new ProductStore();
        productStore.setProduct(p);
        productStore.setStore(store);
        productStore.setStockQuantity(stockQuantity);

        p.getStoreProducts().add(productStore);

        productRepo.save(p);

        Product savedProd =  productRepo.save(p);

        return savedProd;
    }

    @Transactional
    public CategoryGroup findByGroupName(String groupName) {
        return categoryGroupRepository.findByGroupName(groupName)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리 그룹 없음: " + groupName));
    }

    @Transactional
    public Category findByCateCode(String cateCode) {
        return categoryRepository.findByCateCode(cateCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리 없음: " + cateCode));
    }

    @Transactional
    public Color findByColorCode(String colorCode) {
        return colorRepository.findByColorCode(colorCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 색상 없음: " + colorCode));
    }

    @Transactional
    public Size findBySize(String size) {
        return sizeRepository.findBySize(size)
                .orElseThrow(() -> new IllegalArgumentException("해당 사이즈 없음: " + size));
    }

    // ✅ 전체 상품 조회
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    // ✅ 상세 상품 조회
    public Optional<Product> findById(Long id) {
        return productRepo.findById(id);
    }

    // ✅ 상품 수정 메서드
    @Transactional
    public Optional<Product> updateProduct(Long id, ProductUpdateDto updateDto) {
        return productRepo.findById(id).map(product -> {
            product.setProdName(updateDto.getProdName());
            product.setProdCode(updateDto.getProdCode());
            product.setProdDetail(updateDto.getProdDetail());
            product.setStockPrice(updateDto.getStockPrice());
            product.setSellPrice(updateDto.getSellPrice());
            product.setStockQuantity(updateDto.getStockQuantity());

            // 카테고리 업데이트 (존재하는 경우만)
            categoryRepository.findById(updateDto.getCategoryId())
                    .ifPresent(product::setProdCate);

            // 카테고리 그룹 업데이트 (존재하는 경우만)
            categoryGroupRepository.findById(updateDto.getCategoryGroupId())
                    .ifPresent(product::setCateGroup);

            // 색상 업데이트 (존재하는 경우만)
            colorRepository.findById(updateDto.getColorId())
                    .ifPresent(product::setColorCode);

            // 사이즈 업데이트 (존재하는 경우만)
            sizeRepository.findById(updateDto.getSizeId())
                    .ifPresent(product::setSize);

            return product;
        });
    }

    // ✅ 상품 삭제 메서드
    @Transactional
    public boolean deleteProduct(Long id) {
        if (productRepo.existsById(id)) {  // ✅ 상품이 존재하는지 확인
            productRepo.deleteById(id);
            return true;
        }
        return false;  // ✅ 존재하지 않으면 false 반환
    }
}

