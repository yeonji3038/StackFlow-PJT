package ssafy.StackFlow.Service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.category.CategoryGroup;
import ssafy.StackFlow.Repository.category.CategoryGroupRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryGroupService {
    private final CategoryGroupRepository categoryGroupRepository;

    public List<CategoryGroup> findAllCategoryGroups() {
        return categoryGroupRepository.findAll();
    }

    public CategoryGroup findById(Long id) {
        return categoryGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category group ID: " + id));
    }
}