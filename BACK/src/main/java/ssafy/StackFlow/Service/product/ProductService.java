package ssafy.StackFlow.Service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.RT.RT;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.category.CategoryGroup;
import ssafy.StackFlow.Domain.product.*;
import ssafy.StackFlow.Repository.StoreRepository;
import ssafy.StackFlow.Repository.category.CategoryGroupRepository;
import ssafy.StackFlow.Repository.category.CategoryRepository;
import ssafy.StackFlow.Repository.product.*;

import java.util.List;

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
}

