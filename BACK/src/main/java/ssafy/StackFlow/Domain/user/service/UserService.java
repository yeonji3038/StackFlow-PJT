package ssafy.StackFlow.Domain.user.service;

import org.apache.catalina.User;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ssafy.StackFlow.Domain.store.entity.Store;
import ssafy.StackFlow.Domain.user.entity.Role;
import ssafy.StackFlow.Domain.user.entity.Signup;
import ssafy.StackFlow.Domain.store.repository.StoreRepository;
import ssafy.StackFlow.Domain.user.repository.UserRepository;
import ssafy.StackFlow.Domain.notice.service.DataNotFoundException;
import ssafy.StackFlow.Domain.user.DTO.UserDto;
import ssafy.StackFlow.Domain.user.DTO.LoginDto;
import ssafy.StackFlow.global.config.JwtTokenProvider;
import ssafy.StackFlow.global.utill.SecurityUtil;


import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityUtil securityUtil;


    // 현재 사용자 조회
    public Signup getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("로그인된 사용자 없음");
        }

        String username = authentication.getName();
        return userRepository.findByusername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자 찾지 못함: " + username));
    }


    // 회원가입
//    public UserDto signup(UserDto userDto) {
//        // 비밀번호 확인
//        if (!userDto.getPassword().equals(userDto.getPassword2())) {
//            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
//        }
//
//        // 비밀번호 암호화
//        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
//
//        // Store 객체 가져오기 (매장 사용자의 경우 StoreCode로 매장 정보 확인)
//        Store store = null;
//        if (userDto.getStoreId() != null) {
//            store = storeRepository.findByStoreCode(String.valueOf(userDto.getStoreId()))  // StoreCode로 매장 찾기
//                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 매장 코드입니다."));
//        }
//
//        // 역할 설정
//        Role role = (userDto.getStoreId() != null) ? Role.ROLE_USER : Role.ROLE_ADMIN;  // 매장 사용자에게 ROLE_USER 부여, 본사 사용자에게 ROLE_ADMIN 부여
//
//        // UserDto -> Signup 엔티티 변환
//        Signup user = UserDto.toEntity(userDto);
//        user.setPassword(encodedPassword);
//        user.setStore(store);  // Store 설정
//        user.setRole(role);   // 역할 설정 (단일 Role 타입으로 설정)
//
//        // 회원 저장
//        userRepository.save(user);
//
//        // 저장된 회원을 UserDto로 변환하여 반환
//        return UserDto.fromEntity(user);
//    }

    //회원가입
    public UserDto signup(UserDto userDto) {
        // 비밀번호 확인
        if (!userDto.getPassword().equals(userDto.getPassword2())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // UserDto -> Signup 엔티티 변환 (비밀번호 암호화 포함)
        Signup user = UserDto.toEntity(userDto, passwordEncoder);

        Signup savedUser = userRepository.save(user);

        // Signup 엔티티 -> UserDto 변환 후 반환
        return UserDto.fromEntity(savedUser);
    }

    // 사용자 조회
    public Signup getUser(String username) {
        Optional<Signup> signup = this.userRepository.findByusername(username);
        if (signup.isPresent()) {
            return signup.get();
        } else {
            throw new DataNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }

    //로그인



////로그인 API 연결
//    public boolean login(LoginDto loginDto) {
//        // username으로 사용자 정보 조회
//        Optional<Signup> optionalSignup = userRepository.findByusername(loginDto.getUsername());
//
//        // 사용자 존재 여부 확인
//        if (optionalSignup.isPresent()) {
//            Signup signup = optionalSignup.get();
//
//            // 비밀번호 검증
//            if (passwordEncoder.matches(loginDto.getPassword(), signup.getPassword())) {
//                return true; // 로그인 성공
//            }
//        }
//
//        return false; // 로그인 실패
//    }

//    //관리자 API  연결
//    public boolean Admin(LoginDto loginDto) {
//        Optional<Signup> optionalSignup = userRepository.findByusername(loginDto.getUsername());
//
//        if (optionalSignup.isPresent()) {
//            Signup signup = optionalSignup.get();
//
//            if (passwordEncoder.matches(loginDto.getPassword(), signup.getPassword())) {
//                // 관리자 여부 확인
//                if ("ROLE_ADMIN".equals(signup.getRole())) {
//                    return true; // 관리자 로그인 성공
//                }
//            }
//        }
//
//        return false; // 로그인 실패
//    }
}