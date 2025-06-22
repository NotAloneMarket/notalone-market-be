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
	    String upgrade = request.getHeader("Upgrade");

	    // ✅ WebSocket 요청 우회
	    if (
	        (upgrade != null && upgrade.equalsIgnoreCase("websocket")) ||
	        path.startsWith("/ws") ||
	        path.contains("/sockjs") ||
	        path.contains("/info") ||
	        path.contains("/websocket") ||
	        path.contains("/xhr")
	    ) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    // ✅ 인증 없이 허용할 API
	    if (
	        path.equals("/user/login") ||
	        path.equals("/user/register") ||
	        path.startsWith("/posts") ||
	        path.startsWith("/uploads")
	    ) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    // ✅ JWT 인증 로직
	    String authHeader = request.getHeader("Authorization");
	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        String token = authHeader.substring(7);
	        String loginId = jwtUtil.validateTokenAndGetLoginId(token);

	        if (loginId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UsernamePasswordAuthenticationToken authentication =
	                new UsernamePasswordAuthenticationToken(loginId, null,
	                    List.of(new SimpleGrantedAuthority("ROLE_USER")));
	            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	        } else {
	            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
	            return;
	        }
	    } else {
	        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 정보가 없습니다.");
	        return;
	    }

	    filterChain.doFilter(request, response);
	}


}
