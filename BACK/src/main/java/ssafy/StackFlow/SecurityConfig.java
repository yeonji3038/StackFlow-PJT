package ssafy.StackFlow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//로그인 기능
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers("/RT/submit").permitAll()
                        .requestMatchers("/user/login", "/user/register", "/", "/home").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                // 로그인 설정
                .formLogin((formLogin) -> formLogin
                        .loginPage("/user/login")
                        .successHandler((request, response, authentication) -> {
                            // 사용자 권한에 따라 리다이렉트 처리
                            String role = authentication.getAuthorities().iterator().next().getAuthority();
                            if ("ROLE_ADMIN".equals(role)) {
                                response.sendRedirect("/admin"); // 어드민 페이지로 이동
                            } else {
                                response.sendRedirect("/"); // 일반 사용자 메인 페이지로 이동
                            }
                        })
                        .permitAll())
                // 로그아웃 설정
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        .logoutSuccessUrl("/user/login")
                        .invalidateHttpSession(true));

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}