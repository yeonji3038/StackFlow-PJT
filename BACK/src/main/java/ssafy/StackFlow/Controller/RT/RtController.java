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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ssafy.StackFlow.Domain.*;
import ssafy.StackFlow.Domain.RT.RT;
import ssafy.StackFlow.Domain.RT.RtStatus;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.category.CategoryGroup;
import ssafy.StackFlow.Domain.product.Color;
import ssafy.StackFlow.Domain.product.Product;
import ssafy.StackFlow.Domain.product.Size;
import ssafy.StackFlow.Service.RT.RtService;
import ssafy.StackFlow.Service.category.CategoryGroupService;
import ssafy.StackFlow.Service.category.CategoryService;
import ssafy.StackFlow.Service.product.ColorService;
import ssafy.StackFlow.Service.product.SizeService;
import ssafy.StackFlow.Service.store.StoreService;

import java.util.ArrayList;
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

        return "RT/RT";
    }

    @PostMapping("/RT/submit")
    @ResponseBody
    public ResponseEntity<String> submitRT(@RequestBody List<Map<String, Object>> rtDataList) {
        try {
            if (rtDataList == null || rtDataList.isEmpty()) {
                return ResponseEntity.badRequest().body("데이터 없음");
            }

            for (Map<String, Object> rtData : rtDataList) {
                if (!rtData.containsKey("productId") || !rtData.containsKey("storeId") || !rtData.containsKey("reqQuan")) {
                    return ResponseEntity.badRequest().body("필수 데이터 없음");
                }

                Long productId = Long.parseLong(String.valueOf(rtData.get("productId")));
                Long storeId = Long.parseLong(String.valueOf(rtData.get("storeId")));
                int reqQuan = Integer.parseInt(String.valueOf(rtData.get("reqQuan")));

                if (productId <= 0 || storeId <= 0 || reqQuan <= 0) {
                    return ResponseEntity.badRequest().body("유효하지 않은 값");
                }
                
                rtService.createInstruction(productId, storeId, reqQuan);
            }
            return ResponseEntity.ok("Success");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid number format");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request: " + e.getMessage());
        }
    }

    @GetMapping("/RT/list")
    public String rtList(Model model) {
        Store loginStore = rtService.getUserStore();
        List<RT> otherRtList = rtService.getOtherRT();
        List<RT> mtRtList = rtService.getMyRT();
        model.addAttribute("myRtList", mtRtList);
        model.addAttribute("otherRtList", otherRtList);
        model.addAttribute("loginStore", loginStore);
        return "RT/rtList";
    }

    @PostMapping("/processRtRequests")
    public String processRtRequests(
            @RequestParam(value = "selectedRequests", required = false) List<Long> selectedRequests,
            @RequestParam(value = "action", required = false) String action,
            RedirectAttributes redirectAttributes) {

        if (selectedRequests == null || selectedRequests.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "처리할 항목을 선택해주세요.");
            return "redirect:/RT/list";
        }
        try {
            RtStatus status = RtStatus.valueOf(action);
            rtService.updateRtStatus(selectedRequests, status);
            redirectAttributes.addFlashAttribute("message",
                    status == RtStatus.APPROVAL ? "승인 처리가 완료되었습니다." : "거절 처리가 완료되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "처리 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/RT/list";
    }
}

