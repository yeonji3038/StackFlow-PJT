package ssafy.StackFlow.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        boolean isDevMode = "dev".equals(System.getenv("SPRING_PROFILES_ACTIVE"));

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    if (isDevMode) {
                        auth.anyRequest().permitAll();
                    } else {
                        auth
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/swagger-resources/**",
                                        "/api/v3/api-docs/**",
                                        "/api-docs/**",
                                        "/webjars/**",
                                        "/api/rt/submit",
                                        "/user/signup/**",
                                        "/notice/create",
                                        "/notice/api/**",
                                        "/store/**",
                                        "/api/admin/category/**",
                                        "/chat/**",
                                        "/swagger-ui/index.html" // 추가된 부분
                                ).permitAll()
                                .requestMatchers("/admin/**").permitAll()
                                .anyRequest().authenticated();
                    }
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}