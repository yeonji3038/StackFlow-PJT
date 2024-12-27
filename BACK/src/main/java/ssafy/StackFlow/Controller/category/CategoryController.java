package ssafy.StackFlow.Controller.category;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.RequiredArgsConstructor; // 추가된 import
import ssafy.StackFlow.Service.category.CategoryGroupService;
import ssafy.StackFlow.Service.category.CategoryService;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.category.CategoryGroup;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor // 생성자 자동 생성
public class CategoryController {

    private final CategoryService categoryService; // final 추가
    private final CategoryGroupService categoryGroupService; // final 추가

    @GetMapping("/code")
    public String showRegisterForm(Model model) {
        // 카테고리 그룹 목록을 모델에 추가
        model.addAttribute("categoryGroupList", categoryGroupService.findAllCategoryGroups());
        return "admin/code"; // 카테고리 등록 페이지로 이동
    }

    @PostMapping("/code")
    public String registerCategory(@RequestParam("cateGroup") CategoryGroup cateGroup,
                                   @RequestParam String cateCode,
                                   @RequestParam String cateName) {
        // 카테고리 객체 생성
        Category category = new Category();
        category.setCateGroup(cateGroup); // String을 직접 설정
        category.setCateCode(cateCode);
        category.setCateName(cateName);

        // 카테고리 저장
        categoryService.save(category);

        return "redirect:/admin/code"; // 등록 후 다시 폼으로 리다이렉트
    }
}