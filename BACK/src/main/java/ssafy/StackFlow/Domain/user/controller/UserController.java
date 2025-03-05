package ssafy.StackFlow.Domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.user.DTO.*;
import ssafy.StackFlow.Domain.user.service.UserService;
import ssafy.StackFlow.global.docs.UserApiSpecification;
import ssafy.StackFlow.global.response.ApiResponse;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserApiSpecification {

    private final UserService userService; // UserService 주입

    //매장 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserSignupResponseDto>> signupUser(@RequestBody UserDto userDto) {
        UserSignupResponseDto signupUser = userService.signupUser(userDto);
        return ResponseEntity.ok(ApiResponse.success(signupUser));
    }


    // 매장 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserLoginResponseDto>> loginUser(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {
        // 로그인 서비스 호출
        UserLoginResponseDto loginUser = userService.loginUser(userLoginRequestDto);

        // 로그인 성공 후 응답
        return ResponseEntity.ok(ApiResponse.success(loginUser));
    }


    //가입한 매니저 전체 조회
    @GetMapping("/list/all")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> userDtoList = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(userDtoList));
    }


}