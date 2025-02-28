package ssafy.StackFlow.Domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.product.entity.Size;

import java.util.Optional;

public interface SizeRepository extends JpaRepository<Size, Long> {
    Optional<Size> findBySize(String size);

}
