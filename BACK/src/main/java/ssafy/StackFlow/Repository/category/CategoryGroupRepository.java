package ssafy.StackFlow.Repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.category.CategoryGroup;

public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long> {
}
