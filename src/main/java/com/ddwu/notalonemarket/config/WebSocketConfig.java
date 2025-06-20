package com.ddwu.notalonemarket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 프론트에서 연결할 SockJS 엔드포인트 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")  // 프론트와 맞춰서 '/ws'로 설정
        		.setAllowedOriginPatterns("*")
                .withSockJS(); // SockJS 사용
    }

    // 메시지 브로커 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 구독 경로 (메시지 받을 때)
        registry.enableSimpleBroker("/topic");

        // 발행 경로 (메시지 보낼 때)
        registry.setApplicationDestinationPrefixes("/app");
    }
}
