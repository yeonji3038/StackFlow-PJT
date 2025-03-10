package ssafy.StackFlow.Domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.StackFlow.Domain.user.DTO.AdminLoginRequestDto;
import ssafy.StackFlow.Domain.user.DTO.AdminLoginResponseDto;
import ssafy.StackFlow.Domain.user.DTO.AdminSignupDto;
import ssafy.StackFlow.Domain.user.DTO.AdminSignupResponseDto;
import ssafy.StackFlow.Domain.user.service.UserService;
import ssafy.StackFlow.global.docs.AdminApiSpecification;
import ssafy.StackFlow.global.response.ApiResponse;
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AdminController implements AdminApiSpecification {

    private final UserService userService;


    // 본사 회원가입
    @PostMapping("/signup/admin")
    public ResponseEntity<ApiResponse<AdminSignupResponseDto>> signupAdmin(@RequestBody AdminSignupDto adminSignupDto) {
        AdminSignupResponseDto createdAdmin = userService.signupAdmin(adminSignupDto);
        return ResponseEntity.ok(ApiResponse.success(createdAdmin));
    }

    // 본사 로그인
    @PostMapping("/login/admin")
    public ResponseEntity<ApiResponse<AdminLoginResponseDto>> loginAdmin(@RequestBody @Valid AdminLoginRequestDto adminLoginRequestDto) {
        // 로그인 서비스 호출
        AdminLoginResponseDto loginAdmin = userService.loginAdmin(adminLoginRequestDto);

        // 로그인 성공 후 응답
        return ResponseEntity.ok(ApiResponse.success(loginAdmin));
    }

}
