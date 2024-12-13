//enum
//사용자의 권한(역할)을 정의하는 데 사용

package ssafy.StackFlow.user;
import lombok.Getter;

// 사용자의 권한(역할)을 정의하는 열거형
// 시스템 내 사용자의 접근 권한을 구분하는 데 활용

@Getter  //상수는 변경 할 필요없어 @Getter만 사용
public enum UserRole {  //enum(열거자료형)
    ADMIN("ROLE_ADMIN"), //ADMIN -> 상수 , 관리자 권한
    USER("ROLE_USER");   //USER -> 상수 ,일반 관리자 권한

    // 각 권한에 해당하는 문자열 값 저장
    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
