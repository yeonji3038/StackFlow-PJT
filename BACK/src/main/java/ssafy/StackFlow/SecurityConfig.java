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
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
//                        .requestMatchers("/admin/**").hasRole("ADMIN") // ADMIN 역할만 접근 가능
//                        .anyRequest().authenticated()) // 나머지 요청은 인증 필요

                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                //로그인 설정
                .formLogin((formLogin) -> formLogin
                        .loginPage("/user/login")// 페이지 url
//                        .defaultSuccessUrl("/admin")) // 로그인 성공 시 관리자 페이지로 리다이렉트
                        .defaultSuccessUrl("/")) //  로그인 성공시 페이지는 루트url(/)임
//                        .loginPage("/user/login") // 로그인 페이지 URL
//                        .defaultSuccessUrl("/admin") // 로그인 성공 시 관리자 페이지로 리다이렉트
//                        .permitAll()) // 모든 사용자에게 로그인 페이지 접근 허용

                //로그아웃
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true))  //.invalidateHttpSession(true)를 통해 로그아웃 시 생성된 사용자 세션도 삭제하도록 처리
        ;

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //로그인
    @Bean
    //AuthenticationManager는 스프링 시큐리티의 인증을 처리함
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
        //AuthenticationManager는 사용자 인증 시 앞에서 작성한 UserSecurityService와 PasswordEncoder를 내부적으로 사용하여 인증과 권한 부여 프로세스를 처리
    }
}