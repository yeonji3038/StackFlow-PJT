package ssafy.StackFlow.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.Product;
import ssafy.StackFlow.Repository.ProductRepo;
import ssafy.StackFlow.Repository.RtRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RtService {

    private final ProductRepo productRepo;
    private final RtRepository rtRepository;

    public Product findProduct(Long productId) {
        return productRepo.findOne(productId);
    }

    public List<Product> findProducts() {
        return productRepo.findAll();
    }

    public List<Product> search(String keyword) {
        return rtRepository.findByProdCodeContaining(keyword);
    }
}