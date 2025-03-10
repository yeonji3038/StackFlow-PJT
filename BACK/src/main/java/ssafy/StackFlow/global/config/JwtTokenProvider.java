package ssafy.StackFlow.global.config;

import io.jsonwebtoken.*;
import javax.crypto.SecretKey;
import java.util.ArrayList;

import io.jsonwebtoken.security.Keys;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ssafy.StackFlow.Domain.store.entity.Store;
import ssafy.StackFlow.Domain.user.entity.Role;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenProvider{
    private final SecretKey secretKey;
    private final long validityInMilliseconds = TimeUnit.HOURS.toMillis(10); // 10시간 유효기간

    // SecretKey를 애플리케이션 시작 시 한 번만 생성
    public JwtTokenProvider() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    // 로그인할 때마다 새로운 JWT 토큰 발급 (username 사용)
    public String createToken(String username, Role role, Store store) {
        Claims claims = Jwts.claims().setSubject(username); // 사용자 ID 저장

        claims.put("role", role.getRole());  // 역할(role) 추가

        if (role == Role.USER && store != null) {
            claims.put("store_code", store.getStoreCode());
        }

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) // 토큰 발급 시간
                .setExpiration(validity) // 만료 시간 설정
                .signWith(secretKey)  // secretKey를 사용하여 서명
                .compact();
    }



    //  JWT 토큰에서 추출
    public Map<String, Object> getTokenInfo(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // role이 없는 경우 기본값 USER 설정
        Role role;
        try {
            String roleStr = claims.get("role", String.class);
            role = (roleStr != null) ? Role.valueOf(roleStr) : Role.USER;
        } catch (IllegalArgumentException | NullPointerException e) {
            role = Role.USER; // 예외 발생 시 기본값 USER
        }

        String storeCode = claims.get("store_code", String.class);

        return Map.of(
                "username", claims.getSubject(),
                "role", role,
                "store_code", (storeCode != null) ? storeCode : "" // store_code가 없으면 빈 문자열 반환
        );
    }


    // JWT 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("JWT 토큰이 만료되었습니다.");
        } catch (MalformedJwtException e) {
            System.out.println("잘못된 JWT 서명입니다.");
        } catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    // JWT 토큰에서 인증 객체 추출 (username 사용)
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject(); //username 사용
        String roleStr = claims.get("role", String.class);
        Role role = (roleStr != null) ? Role.valueOf(roleStr) : Role.USER;

//        UserDetails userDetails = new User(username, "", new ArrayList<>());
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_" + role.name());

        UserDetails userDetails = new User(username, "", authorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


}


//@Component
//Spring이 자동으로 빈 등록
//@Autowired 또는 @RequiredArgsConstructor로 어디서든 주입해서 사용 가능