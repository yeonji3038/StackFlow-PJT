package ssafy.StackFlow.Controller.admin;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Domain.user.Signup;
import ssafy.StackFlow.Repository.user.UserRepository;
import ssafy.StackFlow.Service.store.StoreService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {
    @Autowired
    private StoreService storeService;
    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("/admin")
    public String adminPage() {
        return "admin"; // admin.html을 반환
    }


     @GetMapping("/admin/registerStore")
     public String registerStorePage() {
         return "admin/registerStore"; // 매장 등록 페이지로 이동
     }

     @PostMapping("/admin/registerStore")
     public String registerStore(@RequestParam (value="storeName") String storeName, @RequestParam (value="location") String location, Model model) {
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
    @GetMapping("/admin/store")
    public String storePage(Model model) {
        List<Store> stores = storeService.findAllStores(); // 모든 매장 가져오기
        Map<Long, List<Signup>> storeUsersMap = new HashMap<>();

        for (Store store : stores) {
            List<Signup> users = userRepository.findByStore(store);
            storeUsersMap.put(store.getId(), users);
        }

        model.addAttribute("stores", stores);
        model.addAttribute("storeUsersMap", storeUsersMap);

        return "admin/store"; // 매장 조회 페이지
    }
}
