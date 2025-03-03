package ssafy.StackFlow.Domain.user.DTO;

import lombok.*;
import ssafy.StackFlow.Domain.store.entity.Store;
import ssafy.StackFlow.Domain.user.entity.Role;
import ssafy.StackFlow.Domain.user.entity.Signup;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminLoginResponseDto {
    private Long id;
    private String username;
    private String name;
    private String email;
    private Role role;
    private String token;

    // Signup 엔티티를 AdminLoginResponseDto로 변환하는 메서드
    public static AdminLoginResponseDto fromEntity(Signup signup, String token) {
        return AdminLoginResponseDto.builder()
                .id(signup.getId())
                .username(signup.getUsername())
                .name(signup.getName())
                .email(signup.getEmail())
                .role(signup.getRole())
                .token(token)  // 로그인 후 발급받은 JWT 토큰
                .build();
    }

}