package com.ddwu.notalonemarket.config;

import com.ddwu.notalonemarket.util.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                	"/uploads/**", // ✅ 이미지 URL 허용 추가
                    "/user/login", 
                    "/user/register"
                ).permitAll()

                // WebSocket 엔드포인트 및 SockJS 관련 경로 허용
                .requestMatchers(
                    "/ws/**",         // 반드시 허용해야 함 (핸드셰이크 경로)
                    "/app/**",        // 메시지 송신 경로
                    "/topic/**"       // 메시지 구독 경로
                ).permitAll()

                // 비로그인 허용 REST API
                .requestMatchers(
                    "/posts", 
                    "/posts/**", 
                    "/chatrooms", 
                    "/chatrooms/**"
                ).permitAll()

                // 로그인 필요
                .requestMatchers(
                    "/user/profile", 
                    "/user/password", 
                    "/posts/my", 
                    "/chatrooms/*/messages", 
                    "/buyHistory"
                ).authenticated()

                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}