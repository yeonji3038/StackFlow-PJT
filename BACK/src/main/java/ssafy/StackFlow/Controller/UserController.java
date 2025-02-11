package ssafy.StackFlow.Controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import ssafy.StackFlow.Domain.user.UserCreateForm;
import ssafy.StackFlow.Repository.user.UserRepository;
import ssafy.StackFlow.Service.user.UserService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;// final 필드
    private final UserRepository userRepository;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup";
        }

        // 매장 코드를 추가로 전달
        userService.create(userCreateForm.getUsername(),
                userCreateForm.getEmail(), userCreateForm.getPassword1(), "ROLE_USER", userCreateForm.getStoreCode()); // 매장 코드 추가

        return "redirect:/"; // 회원가입 후 리다이렉트
    }
    @GetMapping("/login")  //user/login URL로 들어오는 GET 요청을 이 메서드가 처리  -> user/login URL을 매핑
    public String login() {
        return "login";  //매핑한 login 메서드는 login.html 템플릿을 출력
    }
    // 실제 로그인을 진행하는 @PostMapping 방식의 메서드는 스프링 시큐리티가 대신 처리하므로 우리가 직접 코드를 작성하여 구현할 필요x

}