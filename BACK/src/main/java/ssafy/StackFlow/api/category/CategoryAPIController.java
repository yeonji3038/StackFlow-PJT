//package ssafy.StackFlow.api.category;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import ssafy.StackFlow.Domain.category.Category;
//import ssafy.StackFlow.Domain.category.CategoryGroup;
//import ssafy.StackFlow.Service.category.CategoryGroupService;
//import ssafy.StackFlow.Service.category.CategoryService;
//
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/admin")
//@RequiredArgsConstructor
//public class CategoryAPIController {
//
//    private final CategoryService categoryService;
//    private final CategoryGroupService categoryGroupService;
//
//
//    @PostMapping("/category/create")
//    public ResponseEntity<Category> category(@RequestBody CategoryDto categoryDto) {
//        Category createdCategory = categoryService.category(categoryDto); // 생성된 사용자 정보 반환
////        Category createdCategoryGrop = categoryGroupService.category(categoryDto); // 생성된 사용자 정보 반환
//        return ResponseEntity.ok(createdCategory); // 생성된 사용자 정보 반환

//    }
//
//    @GetMapping("/category")
//    public List<CategoryDto> getCategories(
//            @RequestParam(required = false) String cateCode,
//            @RequestParam(required = false) Long groupId,
//            @RequestParam(required = false) String cateName) {
//
//        List<Category> categories = categoryService.findAllCategories();
//        categories.forEach(category -> {
//            System.out.println("=== 카테고리 정보 ===");
//            System.out.println("카테고리 코드: " + category.getCateCode());
//            System.out.println("카테고리 이름: " + category.getCateName());
//            System.out.println("그룹 ID: " + category.getCateGroup().getId());
//            System.out.println("그룹 이름: " + category.getCateGroup().getGroupName());
//            System.out.println("==================");
//        });
//
//        return categories.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//
//
//    @GetMapping("/categoryGroup")
//    public List<CategoryGroup> getCategoryGroups() {
//        return categoryGroupService.findAllCategoryGroups();
//    }
//
=======
//    }
//
//    @GetMapping("/category")
//    public List<CategoryDto> getCategories(
//            @RequestParam(required = false) String cateCode,
//            @RequestParam(required = false) Long groupId,
//            @RequestParam(required = false) String cateName) {
//
//        List<Category> categories = categoryService.findAllCategories();
//        categories.forEach(category -> {
//            System.out.println("=== 카테고리 정보 ===");
//            System.out.println("카테고리 코드: " + category.getCateCode());
//            System.out.println("카테고리 이름: " + category.getCateName());
//            System.out.println("그룹 ID: " + category.getCateGroup().getId());
//            System.out.println("그룹 이름: " + category.getCateGroup().getGroupName());
//            System.out.println("==================");
//        });
//
//        return categories.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//
//
//    @GetMapping("/categoryGroup")
//    public List<CategoryGroup> getCategoryGroups() {
//        return categoryGroupService.findAllCategoryGroups();
//    }
//

////    private CategoryDto convertToDto(Category category) {
////        CategoryDto dto = new CategoryDto();
////        List<CategoryGroup> groups = categoryGroupService.findAllCategoryGroups();
////        dto.setCateCode(category.getCateCode());
////        dto.setCateName(category.getCateName());
////        CategoryGroup categoryGroup = category.getCateGroup();
////        for(group in categoryGroup) {
////
//
////        }
////        dto.setGroupId(category.getCateGroup());
////        return dto;
////    }
//
//}
//
////
////import lombok.RequiredArgsConstructor;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////import ssafy.StackFlow.Domain.category.Category;
////import ssafy.StackFlow.Domain.category.CategoryGroup;
////import ssafy.StackFlow.Service.category.CategoryGroupService;
////import ssafy.StackFlow.Service.category.CategoryService;
////
////
////import java.util.List;
////import java.util.stream.Collectors;
////
////@RestController
////@RequestMapping("/api/admin")
////@RequiredArgsConstructor
////public class CategoryAPIController {
////
////    private final CategoryService categoryService;
////    private final CategoryGroupService categoryGroupService;
////
////
////    @PostMapping("/category/create")
////    public ResponseEntity<Category> category(@RequestBody CategoryDto categoryDto) {
////        Category createdCategory = categoryService.category(categoryDto); // 생성된 사용자 정보 반환
////        Category createdCategoryGrop = categoryGroupService.category(categoryDto); // 생성된 사용자 정보 반환
////        return ResponseEntity.ok(createdCategory); // 생성된 사용자 정보 반환
////    }
////
////    @GetMapping("/category")
////    public List<CategoryDto> getCategories(
////            @RequestParam(required = false) String cateCode,
////            @RequestParam(required = false) Long groupId,
////            @RequestParam(required = false) String cateName) {
////
////        List<Category> categories = categoryService.findAllCategories();
////        categories.forEach(category -> {
////            System.out.println("=== 카테고리 정보 ===");
////            System.out.println("카테고리 코드: " + category.getCateCode());
////            System.out.println("카테고리 이름: " + category.getCateName());
////            System.out.println("그룹 ID: " + category.getCateGroup().getId());
////            System.out.println("그룹 이름: " + category.getCateGroup().getGroupName());
////            System.out.println("==================");
////        });
////
////        return categories.stream()
////                .map(this::convertToDto)
////                .collect(Collectors.toList());
////    }
////
////
////
////    @GetMapping("/categoryGroup")
////    public List<CategoryGroup> getCategoryGroups() {
////        return categoryGroupService.findAllCategoryGroups();
////    }
////
////    private CategoryDto convertToDto(Category category) {
////        CategoryDto dto = new CategoryDto();
////        List<CategoryGroup> groups = categoryGroupService.findAllCategoryGroups();
////        dto.setCateCode(category.getCateCode());
////        dto.setCateName(category.getCateName());
////        CategoryGroup categoryGroup = category.getCateGroup();
//////        for(group in categoryGroup) {
//////
//////        }
////        dto.setGroupId(category.getCateGroup());
////        return dto;
////    }
////
////}

//
=======
//

