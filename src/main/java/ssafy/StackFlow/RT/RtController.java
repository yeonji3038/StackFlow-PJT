package ssafy.StackFlow.RT;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ssafy.StackFlow.Product.Product;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RtController {
    private final RtService RtService;

    @GetMapping("/RT")
    public String list(Model model) {
        List<Product> products = RtService.findProducts();
        model.addAttribute("products", products);
        return "RT";
    }
}

