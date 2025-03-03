package ssafy.StackFlow.Domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.user.DTO.AdminSignupDto;
import ssafy.StackFlow.Domain.user.DTO.AdminSignupResponseDto;
import ssafy.StackFlow.Domain.user.service.UserService;
import ssafy.StackFlow.Domain.user.DTO.UserDto;
import ssafy.StackFlow.global.docs.UserApiSpecification;
import ssafy.StackFlow.global.response.ApiResponse;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserApiSpecification {

    private final UserService userService; // UserService 주입

    //회원가입 APi 연결
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDto>> signupUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.signupUser(userDto); // 생성된 사용자 정보 반환
        return ResponseEntity.ok(ApiResponse.success(createdUser)); // 생성된 사용자 정보 반환
    }

    // 본사 회원가입
    @PostMapping("/signup/admin")
    public ResponseEntity<ApiResponse<AdminSignupResponseDto>> signupAdmin(@RequestBody AdminSignupDto adminSignupDto) {
        AdminSignupResponseDto createdAdmin = userService.signupAdmin(adminSignupDto);
        return ResponseEntity.ok(ApiResponse.success(createdAdmin));
    }


}