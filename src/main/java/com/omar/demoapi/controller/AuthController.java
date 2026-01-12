package com.omar.demoapi.controller;

import com.omar.demoapi.dto.LoginRequest;
import com.omar.demoapi.dto.LoginResponse;
import com.omar.demoapi.security.UserPrincipal;
import com.omar.demoapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    // Test Security Context
    @GetMapping("/me")
    public ResponseEntity<String> me(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return ResponseEntity.ok(principal.getEmail());
    }
}
