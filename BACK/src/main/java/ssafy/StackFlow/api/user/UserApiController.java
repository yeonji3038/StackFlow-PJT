package ssafy.StackFlow.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ssafy.StackFlow.Service.user.UserService;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService; // UserService 주입

    @PostMapping("/api/user/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto userDto) {
        userService.signup(userDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }
}
