package ssafy.StackFlow.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.Color;

public interface ColorRepository extends JpaRepository<Color, Long> {
}

