package com.joboffersapi.domain.usercrud;

import com.joboffersapi.domain.usercrud.dto.RegisterRequest;
import com.joboffersapi.domain.usercrud.dto.UserDto;
import com.joboffersapi.domain.usercrud.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
public class UserFacade {

    private final UserService userService;

    public UserResponseDto register(RegisterRequest requestDto) {
        return userService.registerUser(requestDto);
    }

    public UserDto findUserByUsername(String username) {
        return userService.findByUsername(username);
    }
}
