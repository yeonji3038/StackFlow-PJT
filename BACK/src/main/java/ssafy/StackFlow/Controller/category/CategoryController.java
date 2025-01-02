package ssafy.StackFlow.Controller.category;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.RequiredArgsConstructor; // 추가된 import
import ssafy.StackFlow.Domain.product.Color;
import ssafy.StackFlow.Repository.category.CategoryGroupRepository;
import ssafy.StackFlow.Service.category.CategoryGroupService;
import ssafy.StackFlow.Service.category.CategoryService;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.category.CategoryGroup;
import ssafy.StackFlow.Service.product.ColorService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor // 생성자 자동 생성
public class CategoryController {

    private final CategoryService categoryService; // final 추가
    private final ColorService colorService; // final 추가
    private final CategoryGroupService categoryGroupService; // final 추가
    private final CategoryGroupRepository categoryGroupRepository;
    @GetMapping("/code")
    public String showRegisterForm(Model model) {
        // 카테고리 그룹 목록을 모델에 추가
        model.addAttribute("categoryGroupList", categoryGroupService.findAllCategoryGroups());
        return "admin/code"; // 카테고리 등록 페이지로 이동
    }

    @PostMapping("/code")
    public String registerCategory(@RequestParam("groupName") String groupName,
                                   @RequestParam("cateCode") String cateCode,
                                   @RequestParam("cateName") String cateName) {
        // 카테고리 객체 생성
        CategoryGroup categoryGroup = new CategoryGroup();
        categoryGroupRepository.save(categoryGroup);
        Category category = new Category();
        categoryGroup.setGroupName(groupName);
        category.setCateCode(cateCode);
        category.setCateName(cateName);
        category.setCateGroup(categoryGroup);
        categoryService.save(category);

        return "redirect:/admin/code"; // 등록 후 다시 폼으로 리다이렉트
    }
   @PostMapping("/color")
    public String ColorCode(@RequestParam("colorCode") String colorCode,
                                   @RequestParam("colorName") String colorName) {
        // 카테고리 객체 생성
        Color color = new Color();
        color.setColorCode(colorCode);
        color.setColorName(colorName);
        colorService.save(color);

        return "redirect:/admin/code"; // 등록 후 다시 폼으로 리다이렉트
    }

}