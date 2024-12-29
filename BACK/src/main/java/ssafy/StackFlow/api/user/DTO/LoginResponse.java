package ssafy.StackFlow.api.user.DTO;

import lombok.Getter;
import lombok.Setter;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Domain.user.Signup;

@Getter @Setter
public class LoginResponse {
    private String message;
    private Signup user;
    private Store store;
    private String jsessionId;
}