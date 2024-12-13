package ssafy.StackFlow.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.Color;
import ssafy.StackFlow.Domain.Size;

import java.util.Optional;

public interface SizeRepository extends JpaRepository<Size, Long> {
    Optional<Size> findBySize(String size);

}
