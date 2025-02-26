package ssafy.StackFlow.Domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.product.entity.Product;
import ssafy.StackFlow.Domain.product.entity.Size;
import ssafy.StackFlow.Domain.product.repository.SizeRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SizeService {
    private final SizeRepository sizeRepository;

    public List<Size> findAllSizes() {
        return sizeRepository.findAll();
    }

    public List<Product> findProductsBySize(String size) {
        Size size1 = sizeRepository.findBySize(size)
                .orElseThrow(() -> new IllegalArgumentException("해당 사이즈가 없습니다."));
        return size1.getProducts();
    }
}