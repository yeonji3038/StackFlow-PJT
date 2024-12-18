package ssafy.StackFlow.Repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.category.CategoryGroup;

import java.util.Optional;

public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long> {
    Optional<CategoryGroup> findByGroupName(String groupName);

}
