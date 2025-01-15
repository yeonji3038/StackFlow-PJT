package ssafy.StackFlow.api.user;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import ssafy.StackFlow.api.user.DTO.LoginResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto loginDto, HttpServletResponse response, HttpServletRequest request) {
        boolean loginSuccess = userService.login(loginDto); // 로그인 처리

        if (loginSuccess) {
            String username = loginDto.getUsername();
            Signup signup = userService.getUser(username); // 사용자 정보 조회
//            Cookie cookie = new Cookie("message", URLEncoder.encode("한글입니다.", "UTF-8"));
//            cookie.setMaxAge(60 * 60 * 24 * 30); // 30일
//            response.addCookie(cookie);
            try {
                Cookie cookie = new Cookie("message", URLEncoder.encode("한글입니다.", "UTF-8"));
                cookie.setMaxAge(60 * 60 * 24 * 30); // 30일
                response.addCookie(cookie);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body(null); // 인코딩 오류 발생 시 응답 처리
            }

            HttpSession session = request.getSession();
            session.setAttribute("loginMember", signup); // 세션에 사용자 정보 저장
            session.setAttribute("storeName", signup.getStoreName()); // 매장 이름 세션에 저장

            System.out.println("JSESSIONID 값: " + session.getId());
            System.out.println("사용자 이름: " + signup.getUsername());
            System.out.println("세션에 저장된 매장 이름: " + session.getAttribute("storeName"));

            LoginResponse loginResponse = new LoginResponse();  // 이름 변경
            loginResponse.setUser(signup);
            loginResponse.setStore(signup.getStore());
            loginResponse.setJsessionId(session.getId());

            // 관리자와 일반 사용자 분기 처리
            if ("ROLE_ADMIN".equals(signup.getRole())) {
                loginResponse.setMessage("관리자 로그인이 완료되었습니다."); // 관리자 로그인 성공 메시지
            } else {
                loginResponse.setMessage("로그인이 완료되었습니다."); // 일반 사용자 로그인 성공 메시지
            }
            return ResponseEntity.ok(loginResponse);
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


    @GetMapping("/api/user/info")
    public ResponseEntity<Signup> getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        // 현재 로그인한 사용자 정보 가져오기
        Signup signup = userService.getCurrentUser();

        if (signup == null) {
            return ResponseEntity.status(401).body(null); // 로그인되지 않은 경우
        }

        // 쿠키 생성 및 응답에 추가
        try {
            Cookie userInfoCookie = new Cookie("user_info", URLEncoder.encode(signup.getUsername(), "UTF-8"));
            userInfoCookie.setMaxAge(60 * 60 * 24); // 1일 동안 유지
            userInfoCookie.setHttpOnly(true); // HTTP 전용 쿠키 설정
            response.addCookie(userInfoCookie); // 응답에 쿠키 추가
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null); // 쿠키 생성 중 오류 발생
        }

        // 요청에서 모든 쿠키 가져오기 (디버깅 또는 추가 작업)
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("쿠키 이름: " + cookie.getName());
                System.out.println("쿠키 값: " + cookie.getValue());
            }
        }

        // 사용자 정보 반환
        return ResponseEntity.ok(signup);
    }
}