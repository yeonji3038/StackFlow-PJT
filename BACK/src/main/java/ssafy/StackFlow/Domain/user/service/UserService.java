package ssafy.StackFlow.Domain.user.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ssafy.StackFlow.Domain.user.DTO.*;
import ssafy.StackFlow.Domain.user.entity.Role;
import ssafy.StackFlow.Domain.user.entity.Signup;
import ssafy.StackFlow.Domain.store.repository.StoreRepository;
import ssafy.StackFlow.Domain.user.repository.UserRepository;
import ssafy.StackFlow.Domain.notice.service.DataNotFoundException;
import ssafy.StackFlow.global.config.JwtTokenProvider;
import ssafy.StackFlow.global.utill.SecurityUtil;


import java.util.Optional;

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



    //매장 회원가입
    public UserDto signupUser(UserDto userDto) {
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

    // 본사 회원가입
    public AdminSignupResponseDto signupAdmin(AdminSignupDto adminSignupDTO) {
        // 중복 이메일 체크
        if (userRepository.findByEmail(adminSignupDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 관리자 이메일입니다.");
        }

        // 중복 username 체크
        if (userRepository.findByUsername(adminSignupDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID 입니다.");
        }

        // 비밀번호 일치 여부 확인
        if (!adminSignupDTO.getPassword().equals(adminSignupDTO.getPassword2())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 무조건 ADMIN 역할로 설정
        Role role = Role.ADMIN;

        // DTO를 Entity로 변환하여 저장
        Signup admin = adminSignupDTO.toEntity(passwordEncoder, role);

        userRepository.save(admin);

        // Entity를 AdminSignupResponseDto로 변환하여 반환
        return AdminSignupResponseDto.fromEntity(admin); // fromEntity 메서드를 사용
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

    //본사 로그인
    public AdminLoginResponseDto loginAdmin(AdminLoginRequestDto adminLoginRequestDto) {

        // username으로 관리자 찾기
        Signup admin = (Signup) userRepository.findByUsername(adminLoginRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자 이름을 찾을 수 없습니다."));

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(adminLoginRequestDto.getPassword(), admin.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(admin.getUsername()); // username을 사용하여 토큰 생성

        // AdminLoginResponseDto 생성하여 반환
        return AdminLoginResponseDto.fromEntity(admin, token);
    }





}