package ssafy.StackFlow.Domain.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ssafy.StackFlow.Domain.user.entity.Role;
import ssafy.StackFlow.Domain.user.entity.Signup;

import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminSignupResponseDto {
    private Long id;
    private String username;
    private String name;
    private String password;
    private String password2;
    private String email;
    private Timestamp createdAt;
    private Role role;

    // Signup 엔티티를 AdminSignupResponseDto로 변환하는 메서드
    public static AdminSignupResponseDto fromEntity(Signup signup) {
        return AdminSignupResponseDto.builder()
                .id(signup.getId())
                .username(signup.getUsername())
                .name(signup.getName())
                .email(signup.getEmail())
                .createdAt(signup.getCreatedAt())
                .role(signup.getRole())
                .build();
    }
}
