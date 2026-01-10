package com.omar.demoapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UserPatchRequest {

    private String name;

    @Email(message = "Email must be valid")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    public UserPatchRequest() {}

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
