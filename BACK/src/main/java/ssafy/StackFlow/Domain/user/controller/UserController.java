package ssafy.StackFlow.Domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.user.DTO.*;
import ssafy.StackFlow.Domain.user.service.UserService;
import ssafy.StackFlow.global.docs.UserApiSpecification;
import ssafy.StackFlow.global.response.ApiResponse;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserApiSpecification {

    private final UserService userService; // UserService 주입

    //매장 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDto>> signupUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.signupUser(userDto); // 생성된 사용자 정보 반환
        return ResponseEntity.ok(ApiResponse.success(createdUser)); // 생성된 사용자 정보 반환
    }

//    // 본사 회원가입
//    @PostMapping("/signup/admin")
//    public ResponseEntity<ApiResponse<AdminSignupResponseDto>> signupAdmin(@RequestBody AdminSignupDto adminSignupDto) {
//        AdminSignupResponseDto createdAdmin = userService.signupAdmin(adminSignupDto);
//        return ResponseEntity.ok(ApiResponse.success(createdAdmin));
//    }
//
//    // 본사 로그인
//    @PostMapping("/login/admin")
//    public ResponseEntity<ApiResponse<AdminLoginResponseDto>> loginAdmin(@RequestBody @Valid AdminLoginRequestDto adminLoginRequestDto) {
//        // 로그인 서비스 호출
//        AdminLoginResponseDto loginAdmin = userService.loginAdmin(adminLoginRequestDto);
//
//        // 로그인 성공 후 응답
//        return ResponseEntity.ok(ApiResponse.success(loginAdmin));
//    }


}