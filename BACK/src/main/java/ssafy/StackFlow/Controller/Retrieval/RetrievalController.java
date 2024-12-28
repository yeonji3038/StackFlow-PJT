package ssafy.StackFlow.Controller.Retrieval;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.RT.RT;
import ssafy.StackFlow.Domain.Retrieval.Retrieval;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.category.CategoryGroup;
import ssafy.StackFlow.Domain.product.Color;
import ssafy.StackFlow.Domain.product.Product;
import ssafy.StackFlow.Domain.product.ProductStore;
import ssafy.StackFlow.Domain.product.Size;
import ssafy.StackFlow.Domain.user.Signup;
import ssafy.StackFlow.Repository.Retrieval.RetrievalProductRepository;
import ssafy.StackFlow.Repository.product.ProductStoreRepository;
import ssafy.StackFlow.Repository.user.UserRepository;
import ssafy.StackFlow.Service.Retrieval.RetrievalService;
import ssafy.StackFlow.Service.category.CategoryGroupService;
import ssafy.StackFlow.Service.category.CategoryService;
import ssafy.StackFlow.Service.product.ColorService;
import ssafy.StackFlow.Service.product.ProductService;
import ssafy.StackFlow.Service.product.SizeService;
import ssafy.StackFlow.Service.store.StoreService;
import ssafy.StackFlow.api.Retrieval.dto.ProductStockDto;

import java.util.ArrayList;
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
    private final UserRepository userRepository;
    private final RetrievalProductRepository retrievalProductRepository;
    private final ProductStoreRepository productStoreRepository;

    public Signup getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("로그인된 사용자 없음");
        }

        String username = authentication.getName();

        return userRepository.findByusername(username)
                .orElseThrow(() -> new RuntimeException("해당 사용자 찾지 못함: " + username));
    }
    public Store getUserStore() {
        Signup user = getCurrentUser();
        return user.getStore();
    }

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

        Signup user = getCurrentUser();
        Store loginStore = getUserStore();

        List<Color> colorList = colorService.findAllColors();
        List<Category> categoryList = categoryService.findAllCategories();
        List<Size> sizeList = sizeService.findAllSizes();
        List<CategoryGroup> categoryGroupList = categoryGroupService.findAllCategoryGroups();
        List<Store> storeList = storeService.findAllStores();

        List<ProductStockDto> productStocks = new ArrayList<>();
        if (searchList != null) {
            for (Product product : searchList) {
                ProductStore headOfficeStock = productStoreRepository.findByStoreAndProduct(
                        storeService.findStoreById(1L),
                        product
                ).orElseThrow(() -> new IllegalArgumentException("본사 재고가 존재하지 않습니다."));

                ProductStore storeStock = productStoreRepository.findByStoreAndProduct(
                        loginStore,
                        product
                ).orElse(null);

                productStocks.add(new ProductStockDto(product, headOfficeStock.getStockQuantity(),
                        storeStock != null ? storeStock.getStockQuantity() : 0));
            }
        }

        model.addAttribute("loginStore", loginStore);
        model.addAttribute("searchList", searchList);
        model.addAttribute("productStocks", productStocks);
        model.addAttribute("colorList", colorList);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("sizeList", sizeList);
        model.addAttribute("storeList", storeList);
        model.addAttribute("categoryGroupList", categoryGroupList);
        if (user.isAdmin()) {
            return "Retrieval/Retrieval";
        } else {
            return "Retrieval/Retrieval2";
        }
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

    @PostMapping("/retrieval/submit2")
    @ResponseBody
    public ResponseEntity<String> submitRetrieval2(@RequestBody List<Map<String, Object>> retDataList) {
        try {
            if (retDataList == null || retDataList.isEmpty()) {
                return ResponseEntity.badRequest().body("데이터 없음");
            }

            for (Map<String, Object> retData : retDataList) {
                if (!retData.containsKey("productId") || !retData.containsKey("retQuan")) {
                    return ResponseEntity.badRequest().body("필수 데이터 없음");
                }

                Long productId = Long.parseLong(String.valueOf(retData.get("productId")));
                int retQuan = Integer.parseInt(String.valueOf(retData.get("retQuan")));

                if (productId <= 0 || retQuan <= 0) {
                    return ResponseEntity.badRequest().body("유효하지 않은 값");
                }
                Long storeId = 1L;
                retrievalService.createRetrievalInstruction_User(productId, retQuan);
                Store store = storeService.findStoreById(storeId);
                Product product = productService.findProductById(productId);
                retrievalService.retrievalUser(productId,1L,retQuan);
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
        Signup user = getCurrentUser();
        Store loginStore = getUserStore();
        List<Retrieval> retrievalList = retrievalService.findAllRetrievals();
        model.addAttribute("loginStore", loginStore);
        model.addAttribute("retrievalList", retrievalList);
        return "Retrieval/RetrievalList";
    }
    @GetMapping("/storage/list")
    public String RetrievalList1(Model model) {
        Signup user = getCurrentUser();
        Store loginStore = getUserStore();
        List<Retrieval> retrievalList = retrievalService.findAllRetrievals();
        model.addAttribute("loginStore", loginStore);
        model.addAttribute("retrievalList", retrievalList);
        return "Retrieval/Storage";
    }
}
