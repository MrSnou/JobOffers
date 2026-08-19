package com.joboffersapi.infrastructure.security;

import com.joboffersapi.domain.usercrud.UserFacade;
import com.joboffersapi.domain.usercrud.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collections;

@AllArgsConstructor
@Component
public class LoginUserDetailsService implements UserDetailsService {

    private final UserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(final String username) throws BadCredentialsException {
        UserDto userByUsername = userFacade.findUserByUsername(username);
        return getUser(userByUsername);
    }

    private User getUser(UserDto userDto) {
        return new User(
                userDto.username(),
                userDto.password(),
                Collections.emptyList()
        );
    }
}
