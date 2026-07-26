package com.joboffersapi.domain.usercrud;

import com.joboffersapi.domain.usercrud.dto.LoginRequestDto;
import com.joboffersapi.domain.usercrud.dto.UserDto;
import com.joboffersapi.domain.usercrud.dto.UserRegisterRequestDto;
import com.joboffersapi.domain.usercrud.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class UserFacade {

    private final UserService userService;

    public UserResponseDto login(LoginRequestDto requestDto) {
        // Implement login logic here, e.g., authenticate user and return a response
        // For now, we can return a placeholder response
        return null;
    }

    public UserResponseDto register(@Valid UserRegisterRequestDto requestDto) {
        return userService.registerUser(requestDto);
    }

    public UserDto findUserByUsername(String username) {
        return userService.findByUsername(username);
    }
}
