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
}
