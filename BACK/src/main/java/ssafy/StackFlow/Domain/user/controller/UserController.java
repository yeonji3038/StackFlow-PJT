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

    // 매장 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserLoginResponseDto>> loginUser(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {
        // 로그인 서비스 호출
        UserLoginResponseDto loginUser = userService.loginUser(userLoginRequestDto);

        // 로그인 성공 후 응답
        return ResponseEntity.ok(ApiResponse.success(loginUser));
    }


}