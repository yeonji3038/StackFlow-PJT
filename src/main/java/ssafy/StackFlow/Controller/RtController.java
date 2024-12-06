package ssafy.StackFlow.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ssafy.StackFlow.Domain.Product;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RtController {
    private final ssafy.StackFlow.Service.RtService RtService;

    @GetMapping("/RT")
    public String list(Model model, String keyword) {
        List<Product> searchList;
        if(keyword != null && !keyword.isEmpty()){
            searchList = RtService.search(keyword);
        }else {
            searchList = RtService.findProducts();
        }
        model.addAttribute("searchList", searchList);

        List<Product> products = RtService.findProducts();
        model.addAttribute("products", products);
        return "RT";
    }
}

