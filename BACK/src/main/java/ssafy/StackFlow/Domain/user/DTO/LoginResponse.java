package ssafy.StackFlow.Domain.user.DTO;

import lombok.Getter;
import lombok.Setter;
import ssafy.StackFlow.Domain.store.entity.Store;
import ssafy.StackFlow.Domain.user.entity.Signup;

@Getter @Setter
public class LoginResponse {
    private String message;
    private Signup user;
    private Store store;
    private String jsessionId;
}