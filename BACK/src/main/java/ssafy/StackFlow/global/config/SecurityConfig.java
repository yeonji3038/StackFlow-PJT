package ssafy.StackFlow.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    // PasswordEncoder를 Bean으로 등록하여 프로젝트 전체에서 사용할 수 있도록 설정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final JwtTokenProvider jwtTokenProvider;


    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        boolean isDevMode = "dev".equals(System.getenv("SPRING_PROFILES_ACTIVE"));

        http
                .csrf(csrf -> csrf.disable()) // lambda DSL 방식으로 CSRF 비활성화
                .cors(withDefaults()) // CORS 설정 활성화
//                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable())
//                .httpBasic(httpBasic -> httpBasic.disable())  // HTTP Basic 인증 비활성화
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    if (isDevMode) {
                        auth.anyRequest().permitAll(); // 개발 환경에서는 모든 요청 허용
                    } else {
                        // Swagger 및 특정 경로 허용
                        auth.requestMatchers(
                                        "/v3/api-docs/**",
                                        "/api/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/swagger-resources/**",
                                        "/api-docs/**",
                                        "/webjars/**",
                                        "/api/rt/submit",
                                        "/user/signup/**",
                                        "/notice/create",
                                        "/notice/api/**",
                                        "/store/**",
                                        "/api/admin/category/**",
                                        "/chat/**"
                                ).permitAll()
                                .requestMatchers("/admin/**").permitAll()  // 추가로 인증 필요 없는 경로
                                .anyRequest().authenticated(); // 나머지 요청은 인증 필요
                    }
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}