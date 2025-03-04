package ssafy.StackFlow.global.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ssafy.StackFlow.Domain.user.DTO.*;
import ssafy.StackFlow.global.response.ApiResponse;

import java.util.List;

@Tag(name = "[매장] 회원관리", description = "회원관리 API")
public interface UserApiSpecification {
    @Operation(summary = "매장 회원가입", description = "💡매장 회원가입 합니다.")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDto>> signupUser(@RequestBody UserDto userDto);


    @Operation(summary = "매장 로그인", description = "💡매장 회원가입 합니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserLoginResponseDto>> loginUser(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto);

    @Operation(summary = "가입한 매장 매니저 전체 조회", description = "💡가입한 매장 매니저 전체 조회 합니다.")
    @GetMapping("/list/all")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers();

}
