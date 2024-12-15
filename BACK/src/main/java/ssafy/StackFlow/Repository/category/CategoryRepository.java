package ssafy.StackFlow.Repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
