package ssafy.StackFlow.api.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ssafy.StackFlow.Domain.user.Signup;
import ssafy.StackFlow.Service.user.UserService;
import ssafy.StackFlow.api.user.DTO.LoginDto;
import ssafy.StackFlow.api.user.DTO.UserDto;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService; // UserService 주입

    //회원가입 APi 연결
    @PostMapping("/api/user/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto userDto) {
        userService.signup(userDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

//일반, 관리자 로그인
    @PostMapping("/api/user/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        boolean loginSuccess = userService.login(loginDto); // 로그인 처리

        if (loginSuccess) {
            String username = loginDto.getUsername();
            Signup signup = userService.getUser(username); // 사용자 정보 조회
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", signup); // 세션에 사용자 정보 저장

            // 관리자와 일반 사용자 분기 처리
            if ("ROLE_ADMIN".equals(signup.getRole())) {
                return ResponseEntity.ok("관리자 로그인이 완료되었습니다."); // 관리자 로그인 성공 메시지
            } else {
                return ResponseEntity.ok("로그인이 완료되었습니다."); // 일반 사용자 로그인 성공 메시지
            }
        }

        // 로그인 실패 시
        return ResponseEntity.status(401).body("비밀번호 또는 아이디가 올바르지 않습니다."); // 로그인 실패 메시지 반환
    }

    // 로그아웃 API
    @PostMapping("/api/user/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("loginMember"); // 세션에서 사용자 정보 삭제
        return ResponseEntity.ok("로그아웃되었습니다."); // 로그아웃 메시지 반환
    }
}

