package com.omar.demoapi.security;

import com.omar.demoapi.entity.User;
import com.omar.demoapi.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
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
            writeUnauthorizedResponse(response, "Missing or invalid Authorization header");
            return;
        }

        // Extract token
        String token = authHeader.substring(7);

        // Validate token
        if (!jwtService.validateToken(token)) {
            writeUnauthorizedResponse(response, "Invalid or expired JWT token");
            return;
        }

        // Extract identity (email) from token
        String email = jwtService.extractSubject(token);

        // Load user from database
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create UserPrincipal with role
        UserPrincipal userPrincipal = new UserPrincipal(
                user.getEmail(),
                user.getRole()
        );

        // Create Authentication object
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userPrincipal,
                        null,
                        userPrincipal.getAuthorities()
                );

        // Set authentication in security context
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // Proceed with the filter chain
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        // Skip filtering for authentication endpoints
        return path.equals("/api/auth/login");
    }

    // Helper method to write unauthorized response as JSON
    private void writeUnauthorizedResponse(
            HttpServletResponse response,
            String message
    ) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String json = """
            {
              "error": "Unauthorized",
              "message": "%s"
            }
            """.formatted(message);

        response.getWriter().write(json);
    }
}
