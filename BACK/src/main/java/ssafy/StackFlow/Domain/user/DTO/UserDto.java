package ssafy.StackFlow.Domain.user.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import ssafy.StackFlow.Domain.user.entity.Role;
import ssafy.StackFlow.Domain.user.entity.Signup;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String name;
    private String password;
    private String password2;
    private String email;
    private Timestamp createdAt;
//    private Long storeId;  // StoreId 필드
//    private String storeCode;

    // UserDto -> Signup Entity 변환
    public static Signup toEntity(UserDto userDto, PasswordEncoder passwordEncoder, Role role) {
        return Signup.builder()
                .username(userDto.getUsername())
                .name(userDto.getName())
                .password(passwordEncoder.encode(userDto.getPassword()))  // 🔹 비밀번호 암호화
                .email(userDto.getEmail())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .role(role)
                .build();
    }

    // Entity -> DTO 변환
    public static UserDto fromEntity(Signup signup) {
        return UserDto.builder()
                .id(signup.getId())
                .name(signup.getName())
                .username(signup.getUsername())
                .email(signup.getEmail())
                .createdAt(signup.getCreatedAt())
//                .storeId(signup.getStore() != null ? signup.getStore().getId() : null)  // Store 정보에서 storeId 가져오기
                .build();
    }
}


//DTO(Data Transfer Object)
// 실제 엔터티를 객체를 통해 데이터를 전달하지 않고 DTO를 톻해 데이터를 주고 받음(즉 사용자 정보를 담는 데이터 전송 객체)

//@Data
//Getter와 Setter 메서드: 모든 필드에 대해 접근할 수 있는 메서드

//@NoArgsConstructor: 파라미터가 없는 기본 생성자를 자동으로 생성
//JPA 엔티티 클래스에서 사용 -> JPA 스펙에 따라 기본 생성자가 필요
//Bean 생성 ->  빈 등록 시 기본 생성자가 필요
//DTO 클래스 -> 기본 생성자를 이용해 객체를 생성한 후, setter를 통해 값을 설정해야 할 때 사용


//@AllArgsConstructor: 클래스의 모든 필드를 매개변수로 받는 생성자를 자동으로 생성
// 객체 생성시 모든 필드 한번에 초기화 할때 사용
// 값을 변경 할수 없는 클래스 설계 할때 생성자 통해 필드 초기회 할때 사용
// 테스트 및 간단한 초기화