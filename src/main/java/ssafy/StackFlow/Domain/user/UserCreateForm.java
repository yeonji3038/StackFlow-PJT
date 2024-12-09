package ssafy.StackFlow.Domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3, max = 25)  //username은 입력받는 데이터의 길이가 3~25 사이여야 한다는 검증 조건을 설정
    //@Size는 문자열의 길이가 최소 길이(min)와 최대 길이(max) 사이 인지 검증

    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email;

//
//    public String getPassword1() {
//        return null;
//    }
//
//    public String getPassword2() {
//        return null;
//    }
//
//    public String getUsername() {
//        return null;
//    }
//
//    public String getEmail() {
//        return null;
//    }
}