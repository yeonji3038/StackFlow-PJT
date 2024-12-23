package ssafy.StackFlow.Controller.admin;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Service.store.StoreService;

@Controller
public class AdminController {
    @Autowired
    private StoreService storeService;

    @GetMapping("/admin")
    public String adminPage() {
        return "admin"; // admin.html을 반환
    }
    @GetMapping("/store")
    public String storePage() {
        return "store"; // store.html을 반환
    }

     @GetMapping("/admin/registerStore")
     public String registerStorePage() {
         return "admin/registerStore"; // 매장 등록 페이지로 이동
     }

     @PostMapping("/admin/registerStore")
     public String registerStore(@RequestParam String storeName, @RequestParam String location, Model model) {
         Store store = new Store();
         store.setStoreName(storeName);
         store.setLocation(location);

         // 매장 코드 생성
         String storeCode = storeService.generateStoreCode(location);
         store.setStoreCode(storeCode); // 생성된 매장 코드를 설정

         // 매장 저장
         storeService.saveStore(store);

         // storeCode가 String 타입인지 확인
         model.addAttribute("storeCode", storeCode); // 이 부분에서 오류가 발생할 수 있습니다.
         return "admin/storeRegistrationResult"; // 결과 페이지로 이동
     }
}
