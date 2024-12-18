package ssafy.StackFlow.Controller.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.product.Product;
import ssafy.StackFlow.Repository.category.CategoryGroupRepository;
import ssafy.StackFlow.Repository.category.CategoryRepository;
import ssafy.StackFlow.Repository.product.BrandRepository;
import ssafy.StackFlow.Repository.product.ColorRepository;
import ssafy.StackFlow.Repository.product.SizeRepository;
import ssafy.StackFlow.Service.product.ProductService;

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
    private final BrandRepository brandRepository;

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("categoryGroupList", categoryGroupRepository.findAll());
        model.addAttribute("categoryList", categoryRepository.findAll());
        model.addAttribute("colorList", colorRepository.findAll());
        model.addAttribute("sizeList", sizeRepository.findAll());
        model.addAttribute("brandList", brandRepository.findAll());
        return "product/productForm";
    }

    @PostMapping("/create")
    public String create(@RequestParam String prodName,
                         @RequestParam String prodDetail,
                         @RequestParam String prodCode,
                         @RequestParam String brandCode,
                         @RequestParam String categorygroup,
                         @RequestParam String categoryCode,
                         @RequestParam String colorCode,
                         @RequestParam String size,
                         @RequestParam int stockPrice,
                         @RequestParam int stockQuantity,
                         @RequestParam int sellPrice) {

        productService.create(prodName, prodDetail, prodCode, brandCode, categorygroup, categoryCode, colorCode, size, stockPrice, stockQuantity, sellPrice);

        return "redirect:/product/list";
    }

    @GetMapping("/list")
    public String listProducts(Model model,
                               @RequestParam(required = false) String keyword) {
        List<Product> products;
        if (keyword != null && !keyword.isEmpty()) {
            products = productService.searchProducts(keyword);
        } else {
            products = productService.findAllProducts();
        }
        model.addAttribute("products", products);
        return "product/productList";
    }

    @GetMapping("/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "product/productDetail";
    }
}
