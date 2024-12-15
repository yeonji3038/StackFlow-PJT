package ssafy.StackFlow.Controller.RT;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ssafy.StackFlow.Domain.*;
import ssafy.StackFlow.Domain.RT.RT;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.category.CategoryGroup;
import ssafy.StackFlow.Domain.product.Color;
import ssafy.StackFlow.Domain.product.Size;
import ssafy.StackFlow.Service.RT.RtService;
import ssafy.StackFlow.Service.category.CategoryGroupService;
import ssafy.StackFlow.Service.category.CategoryService;
import ssafy.StackFlow.Service.product.ColorService;
import ssafy.StackFlow.Service.product.SizeService;
import ssafy.StackFlow.Service.store.StoreService;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RtController {
    private final RtService rtService;
    private final ColorService colorService;
    private final CategoryService categoryService;
    private final SizeService sizeService;
    private final CategoryGroupService categoryGroupService;
    private final StoreService storeService;
    @GetMapping("/RT")
public String list(Model model,
              @RequestParam(required = false) String categoryGroup,
              @RequestParam(required = false) String categoryCode,
              @RequestParam(required = false) String colorCode,
              @RequestParam(required = false) String size,
              @RequestParam(required = false) String keyword) {
    List<Product> searchList = null;
    
    if ((keyword != null && !keyword.isEmpty()) ||
        (categoryGroup != null && !categoryGroup.isEmpty()) ||
        (categoryCode != null && !categoryCode.isEmpty()) ||
        (colorCode != null && !colorCode.isEmpty()) ||
        (size != null && !size.isEmpty())) {
        
        if (keyword != null && !keyword.isEmpty()) {
            searchList = rtService.search(keyword);
        } else {
            searchList = rtService.searchByFilters(categoryGroup, categoryCode, colorCode, size);
        }
    }

    List<Color> colorList = colorService.findAllColors();
    List<Category> categoryList = categoryService.findAllCategories();
    List<Size> sizeList = sizeService.findAllSizes();
    List<CategoryGroup> categoryGroupList = categoryGroupService.findAllCategoryGroups();
    List<Store> storeList = storeService.findAllStores();
    
    model.addAttribute("searchList", searchList);
    model.addAttribute("colorList", colorList);
    model.addAttribute("categoryList", categoryList);
    model.addAttribute("sizeList", sizeList);
    model.addAttribute("storeList", storeList);
    model.addAttribute("categoryGroupList", categoryGroupList);

        return "RT";
    }

    @PostMapping("/RT/submit")
    @ResponseBody
    public ResponseEntity<String> submitRT(@RequestBody List<Map<String, Object>> rtDataList) {
        try {
            for (Map<String, Object> rtData : rtDataList) {
                Long productId = Long.parseLong(rtData.get("productId").toString());
                Long storeId = Long.parseLong(rtData.get("storeId").toString());
                int reqQuan = Integer.parseInt(rtData.get("reqQuan").toString());
                
                rtService.createInstruction(productId, storeId, reqQuan);
            }
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/RT/list")
    public String rtList(Model model) {
        List<RT> rtList = rtService.findAllRTs();
        model.addAttribute("rtList", rtList);
        return "rtList";
    }
}

