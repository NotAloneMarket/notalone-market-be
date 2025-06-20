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

                .requestMatchers(
                    "/posts", 
                    "/posts/**", 
                    "/chatrooms", 
                    "/chatrooms/**", 
                    "/app/chat/send"  // WebSocket 메시지도 인증 제외 (필요 시)
                ).permitAll()  // 비로그인 허용이 필요한 API들

                .requestMatchers(
                    "/user/profile", 
                    "/user/password", 
                    "/posts/my", 
                    "/chatrooms/*/messages", 
                    "/buyHistory"
                ).authenticated()  // 로그인 필요

                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
