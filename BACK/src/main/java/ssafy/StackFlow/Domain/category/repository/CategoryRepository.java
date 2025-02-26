package ssafy.StackFlow.Domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.category.entity.Category;

import java.util.Optional;
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCateCode(String cateCode);
}