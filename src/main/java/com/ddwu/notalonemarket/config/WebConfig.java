package com.ddwu.notalonemarket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:5173") // React 주소
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*") 
            .allowCredentials(true);
    }
    
    // 정적 리소스 핸들러 추가 (업로드된 이미지 경로 서빙)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 사용자 홈 디렉토리에 저장된 업로드 경로 (예: /Users/you/notalonemarket/uploads)
        String uploadPath = System.getProperty("user.home") + "/notalonemarket/uploads/";

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath);
    }
}
