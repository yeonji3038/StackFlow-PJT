package ssafy.StackFlow.Domain.user.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import ssafy.StackFlow.Domain.user.entity.Role;
import ssafy.StackFlow.Domain.user.entity.Signup;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminSignupDto {
    private Long id;
    private String username;
    private String name;
    private String password;
    private String password2;
    private String email;
    private Timestamp createdAt;

    // 엔티티로 변환하는 메서드
    public Signup toEntity(PasswordEncoder passwordEncoder, Role role) {
        return Signup.builder()
                .username(this.username)
                .email(this.email)
                .name(this.name)
                .password(passwordEncoder.encode(this.password))
                .role(role)  // 서비스에서 설정된 role 값 사용
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    // 엔티티를 DTO로 변환하는 메서드
    public static AdminSignupDto fromEntity(Signup signup) {
        return AdminSignupDto.builder()
                .id(signup.getId())
                .username(signup.getUsername())
                .email(signup.getEmail())
                .name(signup.getName())
                .createdAt(signup.getCreatedAt())
                .build();
    }

}
