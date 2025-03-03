package ssafy.StackFlow.global.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ssafy.StackFlow.Domain.user.DTO.AdminLoginRequestDto;
import ssafy.StackFlow.Domain.user.DTO.AdminLoginResponseDto;
import ssafy.StackFlow.Domain.user.DTO.AdminSignupDto;
import ssafy.StackFlow.Domain.user.DTO.AdminSignupResponseDto;
import ssafy.StackFlow.global.response.ApiResponse;

@Tag(name = "[본사] 회원관리", description = "회원관리 API")
public interface AdminApiSpecification {
    @Operation(summary = "(관리자) 회원가입", description = """
        💡 관리자 회원가입을 진행합니다.
        
        - 관리자는 본사 시스템에 접근할 수 있는 사용자입니다.
        
        **[ 요청 필드 ]**
        - **username (ID)** : 관리자 ID (아이디로 사용됩니다.)
        - **name** : 관리자의 이름
        - **password** : 관리자의 비밀번호
        - **password2** : 비밀번호 확인 (입력한 비밀번호와 일치해야 합니다)
        - **email** : 관리자의 이메일 주소
        - **createdAt** : 계정이 생성된 날짜 (서버에서 자동 설정)
        
        **[ 응답 필드 ]**
        - **username (ID)** : 관리자 ID
        - **name** : 관리자의 이름
        - **email** : 관리자의 이메일 주소
        - **createdAt** : 계정 생성 날짜
        - **role** : 관리자 권한 ADMIN
        """)
    @PostMapping("/signup/admin")
    public ResponseEntity<ApiResponse<AdminSignupResponseDto>> signupAdmin(@RequestBody AdminSignupDto adminSignupDto);


    @Operation(summary = "(관리자) 로그인", description = """
        💡 관리자의 로그인을 진행합니다.
        
        ---
        
        **[ 요청 필드 ]**
        - **username** : 관리자 ID
        - **password** : 관리자 비밀번호
        
        ---
        
        **[ 응답 필드 ]**
        - **username** : 로그인한 관리자 ID
        - **name** : 로그인한 관리자의 이름
        - **email** : 로그인한 관리자의 이메일
        - **role** : 로그인한 관리자의 권한 (항상 "ADMIN")
        - **token** : 로그인 성공 시 반환되는 JWT 토큰
        
        ---
        
        **[ 설명 ]**
        - 관리자 로그인 시 `username`과 `password`를 입력합니다.
        - 로그인 성공 시, 서버에서 JWT 토큰을 반환하여 후속 요청에 사용됩니다.
        """)
    @PostMapping("/login/admin")
    public ResponseEntity<ApiResponse<AdminLoginResponseDto>> loginAdmin(@RequestBody @Valid AdminLoginRequestDto adminLoginRequestDto);
}
