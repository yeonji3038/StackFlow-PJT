package ssafy.StackFlow.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.Product;
import ssafy.StackFlow.Domain.RT;
import ssafy.StackFlow.Domain.RtProduct;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Repository.ProductRepo;
import ssafy.StackFlow.Repository.RtRepository;
import ssafy.StackFlow.Repository.StoreRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RtService {

    private final ProductRepo productRepo;
    private final RtRepository rtRepository;
    private final StoreRepository storeRepository;

    public Product findProduct(Long productId) {
        return productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    public List<Product> findProducts() {
        return productRepo.findAll();
    }

    public List<Product> search(String keyword) {
        return productRepo.findByProdCodeContaining(keyword);
    }

    public List<Product> searchByFilters(String categoryGroup, String categoryCode, 
                                       String colorCode, String size) {
        String cateGroup = (categoryGroup == null || categoryGroup.trim().isEmpty()) ? null : categoryGroup;
        String cateCode = (categoryCode == null || categoryCode.trim().isEmpty()) ? null : categoryCode;
        String color = (colorCode == null || colorCode.trim().isEmpty()) ? null : colorCode;
        String sizeValue = (size == null || size.trim().isEmpty()) ? null : size;
        
        return productRepo.findByFilters(cateGroup, cateCode, color, sizeValue);
    }

    @Transactional
    public Long createInstruction(Long productId, Long storeId, int reqQuan) {
  
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다."));

        RT rt = RT.createRT(product, store);
        RtProduct rtProduct = RtProduct.createRtProduct(product, reqQuan);
        rt.addRtProduct(rtProduct);        
        
        rtRepository.save(rt);

        return rt.getId();
    }

    public List<RT> findAllRTs() {
        return rtRepository.findAll();
    }
}