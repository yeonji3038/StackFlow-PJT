package ssafy.StackFlow.Controller.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.category.CategoryGroup;
import ssafy.StackFlow.Domain.product.Color;
import ssafy.StackFlow.Domain.product.Product;
import ssafy.StackFlow.Domain.product.Size;
import ssafy.StackFlow.Repository.category.CategoryGroupRepository;
import ssafy.StackFlow.Repository.category.CategoryRepository;
//import ssafy.StackFlow.Repository.product.BrandRepository;
import ssafy.StackFlow.Repository.product.ColorRepository;
import ssafy.StackFlow.Repository.product.SizeRepository;
import ssafy.StackFlow.Service.RT.RtService;
import ssafy.StackFlow.Service.category.CategoryGroupService;
import ssafy.StackFlow.Service.category.CategoryService;
import ssafy.StackFlow.Service.product.ColorService;
import ssafy.StackFlow.Service.product.ProductService;
import ssafy.StackFlow.Service.product.SizeService;
import ssafy.StackFlow.Service.store.StoreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final CategoryGroupRepository categoryGroupRepository;
    private final RtService rtService;
    private final ColorService colorService;
    private final CategoryService categoryService;
    private final SizeService sizeService;
    private final CategoryGroupService categoryGroupService;
    private final StoreService storeService;
//    private final BrandRepository brandRepository;

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("categoryGroupList", categoryGroupRepository.findAll());
        model.addAttribute("categoryList", categoryRepository.findAll());
        model.addAttribute("colorList", colorRepository.findAll());
        model.addAttribute("sizeList", sizeRepository.findAll());
//        model.addAttribute("brandList", brandRepository.findAll());
        return "product/productForm";
    }

    @PostMapping("/create")
    public String create(@RequestParam String prodName,
                         @RequestParam String prodDetail,
                         @RequestParam String prodCode,
//                         @RequestParam String brandCode,
                         @RequestParam String categorygroup,
                         @RequestParam String categoryCode,
                         @RequestParam String colorCode,
                         @RequestParam String size,
                         @RequestParam int stockPrice,
                         @RequestParam int stockQuantity,
                         @RequestParam int sellPrice) {

//        productService.create(prodName, prodDetail, prodCode, brandCode, categorygroup, categoryCode, colorCode, size, stockPrice, stockQuantity, sellPrice);
        productService.create(prodName, prodDetail, prodCode, categorygroup, categoryCode, colorCode, size, stockPrice, stockQuantity, sellPrice);

        return "redirect:/product/list";
    }

    @GetMapping("/list")
    public String listProducts(Model model,
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
                searchList = productService.search(keyword);
            } else {
                searchList = productService.searchByFilters(categoryGroup, categoryCode, colorCode, size);
            }
        } else{
            searchList = productService.findAllProducts();
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

        return "product/productList";
    }

    @GetMapping("/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "product/productDetail";
    }

    // 제품 업데이트 폼을 보여주는 get 메서드
    @GetMapping("update/{id}")
    public String productUpdate(@PathVariable("id") Long id, Model model) {
        Product product = productService.findProductById(id);

        // 기존 제품의 카테고리, 색상, 사이즈 정보 불러오기
        List<CategoryGroup> categoryGroups = categoryGroupService.findAllCategoryGroups();
        List<Category> categories = categoryService.findAllCategories();
        List<Color> colors = colorService.findAllColors();
        List<Size> sizes = sizeService.findAllSizes();

        // 디버깅용 출력
//        System.out.println("Category Groups: " + categoryGroups);
//        System.out.println("Categories: " + categories);
        System.out.println("Category Groups:");
        for (CategoryGroup cg : categoryGroups) {
            System.out.println(" - " + cg.getGroupName());  // ✅ `groupName` 출력 확인
        }

        System.out.println("Categories:");
        for (Category c : categories) {
            System.out.println(" - " + c.getCateCode());  // ✅ `cateCode` 출력 확인
        }
        System.out.println("Colors: " + colors);
        System.out.println("Sizes: " + sizes);

        // 제품 정보 폼에 전달
        model.addAttribute("product", product);
        model.addAttribute("categoryGroups", categoryGroups);
        model.addAttribute("categories", categories);
        model.addAttribute("colors", colors);
        model.addAttribute("sizes", sizes);

        return "product/productUpdate";  // 업데이트 폼으로 이동
    }

    // 제품 업데이트 요청을 처리하는 post 메서드
    @PostMapping("/update/{id}")
    public String productUpdate(@PathVariable("id") Long id,
                                @RequestParam String prodName,
                                @RequestParam String prodCode,
                                @RequestParam String categorygroup,
                                @RequestParam String categoryCode,
                                @RequestParam String colorCode,
                                @RequestParam String size,
                                @RequestParam int stockPrice,
                                @RequestParam int stockQuantity,
                                @RequestParam int sellPrice,
                                @RequestParam String prodDetail) {

        // 기존 제품 조회
        Product existingProduct = productService.findProductById(id);

        // 기존 제품의 정보 업데이트
        existingProduct.setProdName(prodName);
        existingProduct.setProdCode(prodCode);
        existingProduct.setProdDetail(prodDetail);
        existingProduct.setStockPrice(stockPrice);
        existingProduct.setStockQuantity(stockQuantity);
        existingProduct.setSellPrice(sellPrice);
        existingProduct.setProdDetail(prodDetail);

        // 카테고리, 색상, 사이즈 정보 업데이트
        CategoryGroup categoryGroupObj = productService.findByGroupName(categorygroup);
        Category categoryObj = productService.findByCateCode(categoryCode);
        Color colorObj = productService.findByColorCode(colorCode);
        Size sizeObj = productService.findBySize(size);

        existingProduct.setCateGroup(categoryGroupObj);
        existingProduct.setProdCate(categoryObj);
        existingProduct.setColorCode(colorObj);
        existingProduct.setSize(sizeObj);

        // 변경된 제품 정보 저장
        productService.saveProduct(existingProduct);

        // 제품 리스트 페이지로 리다이렉트
        return "redirect:/product/{id}";  // 제품 리스트 페이지로 이동
    }

    @DeleteMapping("/delete/{id}")
    public String productDelete(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/product/list";
    }
}
