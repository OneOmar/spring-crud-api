package com.omar.demoapi.mapper;

import com.omar.demoapi.dto.UserRequest;
import com.omar.demoapi.dto.UserResponse;
import com.omar.demoapi.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Mapping User entity to UserResponse DTO
    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    // Mapping UserRequest DTO to User entity
    public User toEntity(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }
}
