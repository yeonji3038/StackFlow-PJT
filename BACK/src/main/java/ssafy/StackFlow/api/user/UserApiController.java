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
import ssafy.StackFlow.api.user.DTO.StoreDto;
import ssafy.StackFlow.api.user.DTO.UserDto;
import ssafy.StackFlow.api.user.DTO.LoginResponse;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService; // UserService 주입

    //회원가입 APi 연결
    @PostMapping("/api/user/signup")
    public ResponseEntity<Signup> signup(@RequestBody UserDto userDto) {
        Signup createdUser = userService.signup(userDto); // 생성된 사용자 정보 반환
        return ResponseEntity.ok(createdUser); // 생성된 사용자 정보 반환
    }

    //일반, 관리자 로그인
    @PostMapping("/api/user/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        boolean loginSuccess = userService.login(loginDto); // 로그인 처리

        if (loginSuccess) {
            String username = loginDto.getUsername();
            Signup signup = userService.getUser(username); // 사용자 정보 조회
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", signup); // 세션에 사용자 정보 저장
            session.setAttribute("storeName", signup.getStore()); // 매장 이름 세션에 저장

            System.out.println("JSESSIONID 값: " + session.getId());
            System.out.println("사용자 이름: " + signup.getUsername());
            System.out.println("매장 이름: " + signup.getStoreName());



            LoginResponse response = new LoginResponse();
            response.setUser(signup);
            response.setStore(signup.getStore());
            response.setJsessionId(session.getId());

            // 관리자와 일반 사용자 분기 처리
            if ("ROLE_ADMIN".equals(signup.getRole())) {
                response.setMessage("관리자 로그인이 완료되었습니다."); // 관리자 로그인 성공 메시지
            } else {
                response.setMessage("로그인이 완료되었습니다."); // 일반 사용자 로그인 성공 메시지
            }
            return ResponseEntity.ok(response);
        }

        // 로그인 실패 시
        LoginResponse errorResponse = new LoginResponse();
        errorResponse.setMessage("비밀번호 또는 아이디가 올바르지 않습니다.");
        return ResponseEntity.status(401).body(errorResponse); // 로그인 실패 메시지 반환
    }

    // 로그아웃 API
    @PostMapping("/api/user/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("loginMember"); // 세션에서 사용자 정보 삭제
        return ResponseEntity.ok("로그아웃되었습니다."); // 로그아웃 메시지 반환
    }

    // 유저 정보 불러오기
    // @GetMapping("/api/user/info")
    // public ResponseEntity<Signup> getUserInfo(Principal principal) {
    //     if (principal != null) {
    //         // Principal에서 사용자 이름을 가져와서 유저 정보를 조회
    //         String username = principal.getName();
    //         Signup signup = userService.findByUsername(username); // 유저 정보를 가져오는 서비스 메서드 호출

    //         return ResponseEntity.ok(signup);
    //     } else {
    //         return ResponseEntity.status(401).body(null);
    //     }
    // }

    @GetMapping("/api/user/info")
    public ResponseEntity<Signup> getUserInfo() {
        Signup signup = userService.getCurrentUser(); // 현재 로그인한 사용자 정보 가져오기
        return ResponseEntity.ok(signup);
    }
}