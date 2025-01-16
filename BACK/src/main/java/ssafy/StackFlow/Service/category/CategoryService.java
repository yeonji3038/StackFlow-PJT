package ssafy.StackFlow.Service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.category.Category;

import ssafy.StackFlow.Repository.category.CategoryRepository;
import ssafy.StackFlow.api.category.CategoryDto;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }
    @Transactional
    public Category category(CategoryDto categoryDto) {
        // CategoryDto에서 Category 객체로 변환하는 로직을 추가
        Category category = new Category();
        category.setCateCode(categoryDto.getCateCode());
        category.setCateName(categoryDto.getCateName());
        // 추가적인 변환 로직 필요 (예: 카테고리 그룹 설정 등)
        return categoryRepository.save(category);  // 저장 후 반환
    }
    @Transactional // 이 메서드는 데이터베이스에 변경을 가하므로 트랜잭션을 활성화합니다.
    public void save(Category category) {
        categoryRepository.save(category); // 카테고리 저장
    }
}