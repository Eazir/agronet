package com.tyrservices.agronetb.Configs;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.getTokenFromCookie(request);

        if (token != null && jwtUtil.isTokenValid(token)) {
            try {
                Claims claims = jwtUtil.validateToken(token);
                Long userId = Long.parseLong(claims.getSubject());
                String userName = claims.get("userName", String.class);
                String userEmail = claims.get("userEmail", String.class);
                String tipo = claims.get("tipo", String.class);

                JwtUserDetails userDetails = new JwtUserDetails(userId, userName, userEmail, tipo);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authentication);

                HttpSession session = request.getSession(true);
                if (session.getAttribute("userId") == null) {
                    session.setAttribute("userId", userId);
                    session.setAttribute("userName", userName);
                    session.setAttribute("userEmail", userEmail);
                    session.setAttribute("tipo", tipo);
                    session.setMaxInactiveInterval(3600);
                }
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
