//UserSecurityService 스프링 시큐리티가 제공하는 UserDetailsService 인터페이스를 구현
package ssafy.StackFlow.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ssafy.StackFlow.Domain.user.Signup;
import ssafy.StackFlow.Repository.user.UserRepository;
import ssafy.StackFlow.user.UserRole;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    // 사용자 정보 조회를 위한 리포지토리
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //adUserByUsername 메서드는 사용자명으로 Signup 객체를 조회하고, 만약 사용자명에 해당하는 데이터가 없을 경우에는 UsernameNotFoundException을 발생시킴
        // 사용자명으로 사용자 조회
        Optional<Signup> _signup = this.userRepository.findByusername(username);

        // 사용자 미존재 시 예외 발생
        if (_signup.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }

        Signup signup = _signup.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 사용자 권한 설정 (admin/일반 사용자 구분)
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));  //admin’인 경우에는 ADMIN 권한(ROLE_ADMIN)을 부여
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));  //그 이외는 USER 권한(ROLE_USER)을 부여
        }

        // 스프링 시큐리티 User 객체 생성 및 반환
        return new User(signup.getUsername(), signup.getPassword(), authorities);
        // User 객체를 생성해 반환하는데, 이 객체는 스프링 시큐리티에서 사용하며 User 생성자에는 사용자명, 비밀번호, 권한 리스트가 전달
        //시큐리티는 loadUserByUsername 메서드에 의해 리턴된 User 객체의 비밀번호가 사용자로부터 입력받은 비밀번호와 일치하는지를 검사하는 기능을 내부에 가지고 있다.
    }
}