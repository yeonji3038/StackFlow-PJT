package ssafy.StackFlow.api.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.category.CategoryGroup;
import ssafy.StackFlow.Service.category.CategoryGroupService;
import ssafy.StackFlow.Service.category.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class CategoryAPIController {

    private final CategoryService categoryService;
    private final CategoryGroupService categoryGroupService;

    // 카테고리 생성 API
    @PostMapping("/category/create")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto) {
        // CategoryDto를 Category 엔티티로 변환
        Category category = convertToEntity(categoryDto);

        // 카테고리 저장
        categoryService.save(category); // `save()` 메서드를 사용

        return ResponseEntity.ok(category); // 생성된 카테고리 반환
    }

    // 카테고리 목록 조회 API
    @GetMapping("/category")
    public List<CategoryDto> getCategories(
            @RequestParam(required = false) String cateCode,
            @RequestParam(required = false) Long groupId,
            @RequestParam(required = false) String cateName) {

        List<Category> categories = categoryService.findAllCategories();

        return categories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 카테고리 그룹 조회 API
    @GetMapping("/categoryGroup")
    public List<CategoryGroup> getCategoryGroups() {
        return categoryGroupService.findAllCategoryGroups();
    }

    // CategoryDto를 Category 엔티티로 변환하는 메서드
    private Category convertToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        // CategoryDto의 데이터를 Category 엔티티에 복사
        category.setCateCode(categoryDto.getCateCode());
        category.setCateName(categoryDto.getCateName());

        // 카테고리 그룹 ID를 사용해 카테고리 그룹 설정 (필요 시)
        // 예시: category.setCateGroup(categoryGroupService.findById(categoryDto.getGroupId()));

        return category;
    }

    // Category 엔티티를 CategoryDto로 변환하는 메서드
    private CategoryDto convertToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setCateCode(category.getCateCode());
        dto.setCateName(category.getCateName());
        // 카테고리 그룹 정보도 추가
        // 예시: dto.setGroupId(category.getCateGroup().getId());

        return dto;
    }
}
