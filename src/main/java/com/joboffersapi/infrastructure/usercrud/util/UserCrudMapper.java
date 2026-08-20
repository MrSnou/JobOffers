package com.joboffersapi.infrastructure.usercrud.util;

import com.joboffersapi.domain.usercrud.dto.LoginRequest;
import com.joboffersapi.domain.usercrud.dto.RegisterRequest;
import com.joboffersapi.infrastructure.usercrud.dto.UserLoginRequestDto;
import com.joboffersapi.infrastructure.usercrud.dto.UserRegisterRequestDto;

public class UserCrudMapper {

    public static RegisterRequest mapFromUserRegisterRequestDtoToRegisterRequest(UserRegisterRequestDto userRegisterRequestDto) {
        return RegisterRequest.builder()
                .username(userRegisterRequestDto.username())
                .password(userRegisterRequestDto.password())
                .build();
    }

    public static LoginRequest mapFromUserLoginRequestDtoToLoginRequest(UserLoginRequestDto userLoginRequestDto) {
        return LoginRequest.builder()
                .username(userLoginRequestDto.username())
                .password(userLoginRequestDto.password())
                .build();
    }

}
