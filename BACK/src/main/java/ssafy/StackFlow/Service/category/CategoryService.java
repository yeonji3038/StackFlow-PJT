package ssafy.StackFlow.Service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.category.CategoryGroup;
import ssafy.StackFlow.Repository.category.CategoryRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional // 이 메서드는 데이터베이스에 변경을 가하므로 트랜잭션을 활성화합니다.
    public void save(Category category) {
        categoryRepository.save(category); // 카테고리 저장
    }
}