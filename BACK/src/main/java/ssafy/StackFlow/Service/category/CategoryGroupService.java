package ssafy.StackFlow.Service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.category.CategoryGroup;
import ssafy.StackFlow.Repository.category.CategoryGroupRepository;

import java.util.List;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryGroupService {
    private final CategoryGroupRepository categoryGroupRepository;

    public List<CategoryGroup> findAllCategoryGroups() {
        return categoryGroupRepository.findAll();
    }
}