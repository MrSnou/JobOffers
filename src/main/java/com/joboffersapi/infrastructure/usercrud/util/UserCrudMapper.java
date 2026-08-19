package com.joboffersapi.infrastructure.usercrud.util;

import com.joboffersapi.domain.usercrud.dto.RegisterRequest;
import com.joboffersapi.infrastructure.usercrud.dto.UserRegisterRequestDto;

public class UserCrudMapper {

    public static RegisterRequest mapFromUserRegisterRequestDtoToRegisterRequest(UserRegisterRequestDto userRegisterRequestDto) {
        return RegisterRequest.builder()
                .username(userRegisterRequestDto.username())
                .password(userRegisterRequestDto.password())
                .build();
    }

}
