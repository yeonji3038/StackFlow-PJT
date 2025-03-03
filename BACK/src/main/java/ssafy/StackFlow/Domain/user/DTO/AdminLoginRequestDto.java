package ssafy.StackFlow.Domain.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminLoginRequestDto {
    private Long id;
    private String username;
    private String password;


}
