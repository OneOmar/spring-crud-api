package com.omar.demoapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // No authorization header present
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Missing or invalid Authorization header"
            );
            return;
        }

        // Extract token
        String token = authHeader.substring(7);

        // Validate token
        if (!jwtService.validateToken(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
            return;
        }

        // Proceed with the filter chain
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        // Skip filtering for authentication endpoints
        return path.startsWith("/api/auth/");
    }
}
