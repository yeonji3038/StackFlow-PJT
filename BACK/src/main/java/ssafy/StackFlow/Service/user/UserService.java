package ssafy.StackFlow.Service.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ssafy.StackFlow.Domain.user.Signup;
import ssafy.StackFlow.Repository.user.UserRepository;
import ssafy.StackFlow.Service.DataNotFoundException;

import javax.swing.text.html.Option;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

//    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//

    public void create(String username, String email, String password,String role) {
        Signup user = new Signup();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role); // 역할 설정
        this.userRepository.save(user);
    }

    public Signup getUser(String username) {
        Optional<Signup> signup = this.userRepository.findByusername(username);
        if (signup.isPresent()) {
            return signup.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }
}