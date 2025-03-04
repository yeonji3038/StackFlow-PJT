package ssafy.StackFlow.Domain.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ssafy.StackFlow.Domain.user.entity.Role;
import ssafy.StackFlow.Domain.user.entity.Signup;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginResponseDto {
    private Long id;
    private String username; // ID
    private String name;   // 매니저 이름
    private String email;   //이메일
    private Role role;      //권한 : USER
    private String storeName; // 매장 이름
    private String storeCode; // 매장 코드
    private String token;  // 토큰


    public static UserLoginResponseDto fromEntity(Signup signup, String token) {
        return UserLoginResponseDto.builder()
                .id(signup.getId())
                .username(signup.getUsername())
                .name(signup.getName())
                .email(signup.getEmail())
                .role(signup.getRole())
                .storeName(signup.getStore() != null ? signup.getStore().getStoreName() : null) // 매장 이름
                .storeCode(signup.getStore() != null ? signup.getStore().getStoreCode() : null) // 매장 코드
                .token(token)  // 로그인 후 발급받은 JWT 토큰
                .build();
    }
}
