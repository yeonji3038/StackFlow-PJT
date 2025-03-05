package ssafy.StackFlow.Domain.user.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ssafy.StackFlow.Domain.store.entity.Store;
import ssafy.StackFlow.Domain.user.DTO.*;
import ssafy.StackFlow.Domain.user.entity.Role;
import ssafy.StackFlow.Domain.user.entity.Signup;
import ssafy.StackFlow.Domain.store.repository.StoreRepository;
import ssafy.StackFlow.Domain.user.repository.UserRepository;
import ssafy.StackFlow.Domain.notice.service.DataNotFoundException;
import ssafy.StackFlow.global.config.JwtTokenProvider;
import ssafy.StackFlow.global.utill.SecurityUtil;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityUtil securityUtil;


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


    //매장 회원가입
    public UserSignupResponseDto signupUser(UserDto userDto) {
        // 비밀번호 확인
        if (!userDto.getPassword().equals(userDto.getPassword2())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 필수 입력값 확인
        if (userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            throw new IllegalArgumentException("아이디를 입력해야 합니다.");
        }
        if (userDto.getStoreCode() == null || userDto.getStoreCode().isEmpty()) {
            throw new IllegalArgumentException("매장 코드를 입력해야 합니다.");
        }

        // 아이디(이메일) 중복 검사
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        // 매장 코드 존재 여부 검사 (없는 코드일 경우 예외 발생)
        Store store = (Store) storeRepository.findByStoreCode(userDto.getStoreCode())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매장 코드입니다."));

        // 무조건 USER 역할로 설정
        Role role = Role.USER;

        // UserDto -> Signup 엔티티 변환 (비밀번호 암호화 포함)
        Signup user = UserDto.toEntity(userDto, passwordEncoder, role);

        // 매장 정보 설정
        user.setStore(store);

        // 회원 정보 저장
        Signup savedUser = userRepository.save(user);

        // Signup 엔티티 -> UserSignupResponseDto 변환 후 반환
        return UserSignupResponseDto.fromEntity(savedUser);
    }

    //본사 로그인
    public UserLoginResponseDto loginUser(UserLoginRequestDto userLoginRequestDto) {

        // username으로 관리자 찾기
        Signup user = (Signup) userRepository.findByUsername(userLoginRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자 이름을 찾을 수 없습니다."));

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(user.getUsername()); // username을 사용하여 토큰 생성

        // AdminLoginResponseDto 생성하여 반환
        return UserLoginResponseDto.fromEntity(user, token);
    }


//    // 사용자 조회
    public Signup getUser(String username) {
        Optional<Signup> signup = this.userRepository.findByusername(username);
        if (signup.isPresent()) {
            return signup.get();
        } else {
            throw new DataNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }

    // 가입한 매니저 전체 조회
    public List<UserDto> getAllUsers() {
        // role이 USER인 회원만 조회
        List<Signup> users = userRepository.findAllByRole(Role.USER);
        return users.stream()
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());
    }
}