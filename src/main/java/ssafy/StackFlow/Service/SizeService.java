package ssafy.StackFlow.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.Color;
import ssafy.StackFlow.Domain.Product;
import ssafy.StackFlow.Domain.Size;
import ssafy.StackFlow.Repository.ColorRepository;
import ssafy.StackFlow.Repository.SizeRepository;

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