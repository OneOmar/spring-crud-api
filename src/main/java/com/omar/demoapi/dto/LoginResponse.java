package com.omar.demoapi.dto;

public class LoginResponse {

    private final String email;

    public LoginResponse(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
