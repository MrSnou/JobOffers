package com.joboffersapi.domain.usercrud;

import com.joboffersapi.domain.usercrud.dto.RegisterRequest;
import com.joboffersapi.domain.usercrud.dto.UserDto;
import com.joboffersapi.domain.usercrud.dto.UserResponseDto;
import com.joboffersapi.domain.usercrud.exception.UserExistsException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.joboffersapi.domain.usercrud.UserMapper.mapFromUserToUserDto;

@Service
@AllArgsConstructor
class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Transactional
    UserResponseDto registerUser(RegisterRequest requestDto) {
        if (userRepository.existsByUsername(requestDto.username()))
            throw new UserExistsException("Username " + requestDto.username() + " already exists");

        User toSave = User.builder()
                .username(requestDto.username())
                .password(bCryptPasswordEncoder.encode(requestDto.password()))
                .build();
        User saved = userRepository.save(toSave);
        UserDto userDto = mapFromUserToUserDto(saved);
        return UserResponseDto.builder()
                .message(String.format("User %s registered successfully.", saved.getUsername()))
                .userDto(userDto)
                .build();
    }

    UserDto findByUsername(final String username) {
        return mapFromUserToUserDto(
                userRepository.findByUsername(username)
                        .orElseThrow(() -> new BadCredentialsException("Username " + username + " not found")));
    }

}
