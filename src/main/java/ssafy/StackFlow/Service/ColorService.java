package ssafy.StackFlow.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.Color;
import ssafy.StackFlow.Domain.Product;
import ssafy.StackFlow.Repository.ColorRepository;

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
}
