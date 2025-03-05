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
    @Operation(summary = "(매장) 회원가입", description = """
    💡 매장 회원가입을 진행합니다.
    
    - 사용자는 매장 코드와 함께 회원가입을 진행하며, 매장에 대한 정보가 포함됩니다.
    
    **[ 요청 필드 ]**
    - **username (ID)** : 사용자 ID (아이디로 사용됩니다.)
    - **name** : 사용자의 이름
    - **password** : 사용자의 비밀번호
    - **password2** : 비밀번호 확인 (입력한 비밀번호와 일치해야 합니다)
    - **email** : 사용자의 이메일 주소
    - **storeCode** : 매장 코드 (매장과 연관된 사용자여야 합니다.)
    - **createdAt** : 계정이 생성된 날짜 (서버에서 자동 설정)
    
    **[ 응답 필드 ]**
    - **username (ID)** : 사용자 ID
    - **name** : 사용자의 이름
    - **email** : 사용자의 이메일 주소
    - **createdAt** : 계정 생성 날짜
    - **storeId** : 매장 ID
    - **storeCode** : 매장 코드
    - **storeName** : 매장 이름
    """)
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserSignupResponseDto>> signupUser(@RequestBody UserDto userDto) ;


    @Operation(summary = "(매장) 로그인", description = """
    💡 매장 관리자가 로그인합니다.
    
    ---
    
    **[ 요청 필드 ]**
    - **username** : 매장 관리자 ID (아이디로 사용됩니다.)
    - **password** : 매장 관리자 비밀번호
    
    ---
    
    **[ 응답 필드 ]**
    - **username** : 로그인한 매장 관리자 ID
    - **name** : 로그인한 매장 관리자의 이름
    - **email** : 로그인한 매장 관리자의 이메일
    - **role** : 로그인한 매장 관리자의 권한 (항상 "USER")
    - **storeName** : 매장 이름
    - **storeCode** : 매장 코드
    - **token** : 로그인 성공 시 반환되는 JWT 토큰
    
    ---
    
    **[ 설명 ]**
    - 매장 관리자 로그인 시 `username`과 `password`를 입력합니다.
    - 로그인 성공 시, 서버에서 JWT 토큰을 반환하여 후속 요청에 사용됩니다.
    """)

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserLoginResponseDto>> loginUser(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto);

    @Operation(
            summary = "가입한 매장 매니저 전체 조회",
            description = """
        💡가입한 매장 매니저 전체 조회합니다.
        
        **[ 응답 필드 ]**
        - **id** : 매장 관리자 ID
        - **username** : 매장 관리자 아이디
        - **name** : 매장 관리자 이름
        - **email** : 매장 관리자 이메일
        - **createdAt** : 매장 관리자 계정 생성일
        - **storeId** : 매장이 연결된 ID
        - **storeCode** : 매장 코드
        
        ---
        
        **[ 설명 ]**
        - 해당 API는 가입된 모든 매장 매니저 정보를 조회합니다.
    """
    )
    @GetMapping("/list/all")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers();

}
