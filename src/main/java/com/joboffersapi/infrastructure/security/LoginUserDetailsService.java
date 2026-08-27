package com.joboffersapi.infrastructure.security;

import com.joboffersapi.domain.usercrud.UserFacade;
import com.joboffersapi.domain.usercrud.dto.UserDto;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

@AllArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {

    private final UserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(final @NonNull String username) throws BadCredentialsException {
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
