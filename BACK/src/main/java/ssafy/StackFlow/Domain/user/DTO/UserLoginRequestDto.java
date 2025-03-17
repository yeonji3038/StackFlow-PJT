package ssafy.StackFlow.Domain.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRequestDto {
    private Long id;
    private String username;  //ID
    private String password;  //비밀번호


}
