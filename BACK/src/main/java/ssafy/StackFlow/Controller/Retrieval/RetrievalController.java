package ssafy.StackFlow.Controller.Retrieval;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.Retrieval.Retrieval;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.category.CategoryGroup;
import ssafy.StackFlow.Domain.product.Color;
import ssafy.StackFlow.Domain.product.Product;
import ssafy.StackFlow.Domain.product.Size;
import ssafy.StackFlow.Service.Retrieval.RetrievalService;
import ssafy.StackFlow.Service.category.CategoryGroupService;
import ssafy.StackFlow.Service.category.CategoryService;
import ssafy.StackFlow.Service.product.ColorService;
import ssafy.StackFlow.Service.product.ProductService;
import ssafy.StackFlow.Service.product.SizeService;
import ssafy.StackFlow.Service.store.StoreService;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RetrievalController {
    private final RetrievalService retrievalService;
    private final ColorService colorService;
    private final CategoryService categoryService;
    private final SizeService sizeService;
    private final CategoryGroupService categoryGroupService;
    private final StoreService storeService;
    private final ProductService productService;

    @GetMapping("/retrieval")
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
                searchList = retrievalService.search(keyword);
            } else {
                searchList = retrievalService.searchByFilters(categoryGroup, categoryCode, colorCode, size);
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

        return "Retrieval/Retrieval";
    }

    @PostMapping("/retrieval/submit")
    @ResponseBody
    public ResponseEntity<String> submitRetrieval(@RequestBody List<Map<String, Object>> retDataList) {
        try {
            if (retDataList == null || retDataList.isEmpty()) {
                return ResponseEntity.badRequest().body("데이터 없음");
            }

            for (Map<String, Object> retData : retDataList) {
                if (!retData.containsKey("productId") || !retData.containsKey("storeId") || !retData.containsKey("retQuan")) {
                    return ResponseEntity.badRequest().body("필수 데이터 없음");
                }

                Long productId = Long.parseLong(String.valueOf(retData.get("productId")));
                Long storeId = Long.parseLong(String.valueOf(retData.get("storeId")));
                int retQuan = Integer.parseInt(String.valueOf(retData.get("retQuan")));

                if (productId <= 0 || storeId <= 0 || retQuan <= 0) {
                    return ResponseEntity.badRequest().body("유효하지 않은 값");
                }

                retrievalService.createRetrievalInstruction(productId, storeId, retQuan);
                Store store = storeService.findStoreById(storeId);
                Product product = productService.findProductById(productId);
                retrievalService.retrievalAdmin(productId,1L,storeId,retQuan);
            }
            return ResponseEntity.ok("Success");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid number format");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request: " + e.getMessage());
        }
    }

    @GetMapping("/retrieval/list")
    public String RetrievalList(Model model) {
        List<Retrieval> retrievalList = retrievalService.findAllRetrievals();
        model.addAttribute("retrievalList", retrievalList);
        return "Retrieval/RetrievalList";
    }
}
