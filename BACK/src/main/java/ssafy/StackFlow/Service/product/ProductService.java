package ssafy.StackFlow.Service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.category.CategoryGroup;
import ssafy.StackFlow.Domain.product.Brand;
import ssafy.StackFlow.Domain.product.Color;
import ssafy.StackFlow.Domain.product.Product;
import ssafy.StackFlow.Domain.product.Size;
import ssafy.StackFlow.Repository.category.CategoryGroupRepository;
import ssafy.StackFlow.Repository.category.CategoryRepository;
import ssafy.StackFlow.Repository.product.BrandRepository;
import ssafy.StackFlow.Repository.product.ColorRepository;
import ssafy.StackFlow.Repository.product.ProductRepo;
import ssafy.StackFlow.Repository.product.SizeRepository;

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
    private final BrandRepository brandRepository;

    @Transactional
    public void saveProduct(Product product) {
        productRepo.save(product);
    }

    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    public Product findProductById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 상품이 없습니다."));
    }

    public List<Product> searchProducts(String keyword) {
        return productRepo.findByProdCodeContaining(keyword);
    }

    @Transactional
    public void create(String prodName, String prodDetail, String prodCode, String brandCode, String categorygroup, String categoryCode, String colorCode, String size,
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
        Brand brand = brandRepository.findByBrandCode(brandCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 브랜드 없음"));

        Product p = new Product();
        p.setProdName(prodName);
        p.setProdCode(prodCode);
        p.setBrandCode(brand);
        p.setStockPrice(stockPrice);
        p.setStockQuantity(stockQuantity);
        p.setSellPrice(sellPrice);
        p.setProdCate(category);
        p.setColorCode(color);
        p.setSize(productSize);
        p.setCateGroup(categoryGroup);
        p.setProdDetail(prodDetail);
        productRepo.save(p);
    }
}
