package ssafy.StackFlow.Domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ssafy.StackFlow.Domain.store.entity.Store;
import ssafy.StackFlow.Domain.user.entity.Signup;
import ssafy.StackFlow.Domain.store.repository.StoreRepository;
import ssafy.StackFlow.Domain.user.repository.UserRepository;
import ssafy.StackFlow.Domain.notice.service.DataNotFoundException;
import ssafy.StackFlow.Domain.user.DTO.UserDto;
import ssafy.StackFlow.Domain.user.DTO.LoginDto;


import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final PasswordEncoder passwordEncoder;


    // Signup saveEntity(Signup signup);
//    Signup saveDto(UserDto userDto); // DTO를 사용하여 회원 등록

//    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
public Signup getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
        throw new RuntimeException("로그인된 사용자 없음");
    }

    String username = authentication.getName();

    return userRepository.findByusername(username)
            .orElseThrow(() -> new RuntimeException("해당 사용자 찾지 못함: " + username));
}
    public void create(String username, String email, String password, String role, String storeCode) {
        Signup user = new Signup();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role); // 역할 설정

        // 매장 코드로 매장 정보 조회
        Store store = storeRepository.findByStoreCode(storeCode);
        if (store == null) {
            throw new DataNotFoundException("유효하지 않은 매장 코드입니다.");
        }
        user.setStore(store); // Store 객체 설정

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

//회원가입 API 연결
    public Signup signup(UserDto userDto) {
        Signup user = new Signup();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());

        // 기본 역할 설정
        user.setRole(userDto.getRole() != null ? userDto.getRole() : "ROLE_USER"); // 기본값 설정

        // Store 객체를 설정
        Store store = storeRepository.findByStoreCode(userDto.getStoreCode());
        if (store != null) {
            user.setStore(store); // Store 설정
        } else {
            throw new RuntimeException("Store not found with storeCode: " + userDto.getStoreCode());
        }

        return userRepository.save(user); // 데이터베이스 저장
    }

//로그인 API 연결
    public boolean login(LoginDto loginDto) {
        // username으로 사용자 정보 조회
        Optional<Signup> optionalSignup = userRepository.findByusername(loginDto.getUsername());

        // 사용자 존재 여부 확인
        if (optionalSignup.isPresent()) {
            Signup signup = optionalSignup.get();

            // 비밀번호 검증
            if (passwordEncoder.matches(loginDto.getPassword(), signup.getPassword())) {
                return true; // 로그인 성공
            }
        }

        return false; // 로그인 실패
    }
//관리자 API  연결
    public boolean Admin(LoginDto loginDto) {
        Optional<Signup> optionalSignup = userRepository.findByusername(loginDto.getUsername());

        if (optionalSignup.isPresent()) {
            Signup signup = optionalSignup.get();

            if (passwordEncoder.matches(loginDto.getPassword(), signup.getPassword())) {
                // 관리자 여부 확인
                if ("ROLE_ADMIN".equals(signup.getRole())) {
                    return true; // 관리자 로그인 성공
                }
            }
        }

        return false; // 로그인 실패
    }
}