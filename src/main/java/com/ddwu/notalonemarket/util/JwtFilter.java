package com.ddwu.notalonemarket.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {
        String path = request.getRequestURI();
        String authHeader = request.getHeader("Authorization");

        System.out.println("👉 요청 경로: " + path);
        System.out.println("👉 Authorization 헤더: " + authHeader);

        if (path.equals("/thymeleaf-login") || path.startsWith("/login") || path.equals("/user/login") || path.equals("/user/register") || path.startsWith("/uploads")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String loginId = jwtUtil.validateTokenAndGetLoginId(token);

            System.out.println("👉 JWT로부터 추출한 loginId: " + loginId);

            if (loginId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                        loginId,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                    );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                System.out.println("✅ SecurityContextHolder에 인증 정보 설정 완료");
            } else {
                System.out.println("⚠️ 이미 인증된 사용자이거나 loginId가 null입니다.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
                return; // 🔥 인증 실패 시 여기서 요청 종료
            }
        } else {
            System.out.println("⚠️ Authorization 헤더 없음 또는 형식 오류");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 정보가 없습니다.");
            return; // 🔥 인증 실패 시 여기서 요청 종료
        }


        filterChain.doFilter(request, response);
    }


}
