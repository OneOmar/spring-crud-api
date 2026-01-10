package com.omar.demoapi.service;

import com.omar.demoapi.dto.LoginRequest;
import com.omar.demoapi.dto.UserPatchRequest;
import com.omar.demoapi.dto.UserRequest;
import com.omar.demoapi.dto.UserResponse;
import com.omar.demoapi.entity.User;
import com.omar.demoapi.exception.AuthenticationFailedException;
import com.omar.demoapi.exception.UserNotFoundException;
import com.omar.demoapi.mapper.UserMapper;
import com.omar.demoapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    // Dependencies
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // Constructor Injection
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return userMapper.toResponse(user);
    }

    public UserResponse createUser(UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public UserResponse updateUser(Long id, UserRequest request) {

        User user = userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException(id));


        user.setName(request.getName());
        user.setEmail(request.getEmail());

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);

        User updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);
    }

    public UserResponse patchUser(Long id, UserPatchRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getPassword() != null) {
            String hashedPassword = passwordEncoder.encode(request.getPassword());
            user.setPassword(hashedPassword);
        }

        User patchedUser = userRepository.save(user);
        return userMapper.toResponse(patchedUser);
    }

    public void deleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.deleteById(id);
    }

    // Authentication method
    public void login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(AuthenticationFailedException::new);

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new AuthenticationFailedException();
        }

        // Authentication successful
    }
}
