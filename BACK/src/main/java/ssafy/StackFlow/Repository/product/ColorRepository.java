package ssafy.StackFlow.Repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.product.Color;
import java.util.Optional;


public interface ColorRepository extends JpaRepository<Color, Long> {
    Optional<Color> findByColorCode(String colorCode);
}

