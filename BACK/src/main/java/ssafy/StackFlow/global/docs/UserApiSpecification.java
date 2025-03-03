package ssafy.StackFlow.global.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ssafy.StackFlow.Domain.user.DTO.AdminSignupDto;
import ssafy.StackFlow.Domain.user.DTO.AdminSignupResponseDto;
import ssafy.StackFlow.Domain.user.DTO.UserDto;
import ssafy.StackFlow.global.response.ApiResponse;

@Tag(name = "[본사/매장] 회원관리", description = "회원관리 API")
public interface UserApiSpecification {
    @Operation(summary = "매장 매니저 회원가입", description = "💡매장 회원가입 합니다.")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDto>> signupUser(@RequestBody UserDto userDto);

    @Operation(summary = "본사 회원가입", description = "💡 본사 회원가입 합니다.")
    @PostMapping("/signup/admin")
    public ResponseEntity<ApiResponse<AdminSignupResponseDto>> signupAdmin(@RequestBody AdminSignupDto adminSignupDto);
}
