package ssafy.StackFlow.Domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.category.entity.CategoryGroup;
import ssafy.StackFlow.Domain.category.repository.CategoryGroupRepository;

import java.util.List;

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