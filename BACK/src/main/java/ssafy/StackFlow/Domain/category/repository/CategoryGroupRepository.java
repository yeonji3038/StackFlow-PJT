package ssafy.StackFlow.Domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.category.entity.CategoryGroup;

import java.util.Optional;

public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long> {
    Optional<CategoryGroup> findByGroupName(String groupName);

}
