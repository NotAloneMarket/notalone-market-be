package com.ddwu.notalonemarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로
                .allowedOrigins("http://localhost:5173") // 프론트 주소
                .allowedMethods("*") // GET, POST 등 모두 허용
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
