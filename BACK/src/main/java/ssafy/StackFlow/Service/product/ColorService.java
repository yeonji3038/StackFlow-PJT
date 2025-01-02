package ssafy.StackFlow.Service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.product.Color;
import ssafy.StackFlow.Domain.product.Product;
import ssafy.StackFlow.Repository.product.ColorRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ColorService {
    private final ColorRepository colorRepository;

    public List<Color> findAllColors() {
        return colorRepository.findAll();
    }

    public List<Product> findProductsByColor(String colorCode) {
        Color color = colorRepository.findByColorCode(colorCode)
            .orElseThrow(() -> new IllegalArgumentException("해당 색상 코드가 없습니다."));
        return color.getProducts();
    }
    @Transactional // 이 메서드는 데이터베이스에 변경을 가하므로 트랜잭션을 활성화합니다.
    public void save(Color color) {
        colorRepository.save(color); // 카테고리 저장
    }
}
