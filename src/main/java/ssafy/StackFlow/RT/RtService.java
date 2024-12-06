package ssafy.StackFlow.RT;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Product.Product;
import ssafy.StackFlow.Product.ProductRepo;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RtService {

    private final ProductRepo productRepo;

    public Product findProduct(Long productId) {
        return productRepo.findOne(productId);
    }

    public List<Product> findProducts() {
        return productRepo.findAll();
    }

}