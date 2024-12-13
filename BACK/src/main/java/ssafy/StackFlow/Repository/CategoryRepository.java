package ssafy.StackFlow.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}