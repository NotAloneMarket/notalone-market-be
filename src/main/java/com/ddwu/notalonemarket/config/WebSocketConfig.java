package com.ddwu.notalonemarket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat") // 소켓 연결 주소
                .setAllowedOriginPatterns("*") // CORS 허용 (개발 시 *로 두고 운영 시 도메인 지정)
                .withSockJS(); // SockJS 사용 (호환성)
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // 구독 경로 prefix
        registry.setApplicationDestinationPrefixes("/app"); // 클라이언트 보낼 prefix
    }
}
