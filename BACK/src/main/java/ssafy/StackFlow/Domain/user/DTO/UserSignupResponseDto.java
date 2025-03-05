package ssafy.StackFlow.Domain.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ssafy.StackFlow.Domain.user.entity.Signup;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignupResponseDto {
    private Long id;
    private String username;
    private String name;
    private String email;
    private Timestamp createdAt;
    private Long storeId;
    private String storeCode;
    private String storeName;

    // Signup 엔티티를 DTO로 변환하는 메서드
    public static UserSignupResponseDto fromEntity(Signup signup) {
        // Builder 패턴을 사용하여 DTO 객체를 생성
        UserSignupResponseDto.UserSignupResponseDtoBuilder builder = UserSignupResponseDto.builder()
                .id(signup.getId())
                .username(signup.getUsername())
                .name(signup.getName())
                .email(signup.getEmail())
                .createdAt(signup.getCreatedAt());

        // Store 정보가 있을 경우 storeId와 storeCode를 설정
        if (signup.getStore() != null) {
            builder.storeId(signup.getStore().getId())
                    .storeCode(signup.getStore().getStoreCode())
                    .storeName(signup.getStore().getStoreName());
        }

        // Builder 객체에서 최종 DTO 객체 반환
        return builder.build();
    }

}
