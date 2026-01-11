package com.omar.demoapi.controller;

import com.omar.demoapi.dto.LoginRequest;
import com.omar.demoapi.dto.LoginResponse;
import com.omar.demoapi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/me")
//    public ResponseEntity<String> getCurrentUser(HttpServletRequest request) {
//        String email = (String) request.getAttribute("authenticatedUserEmail");
//        // LoginResponse response = userService.getUserByEmail(email);
//        return ResponseEntity.ok(email);
//    }
}
