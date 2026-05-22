package com.joboffersapi.domain.userCRUD;

import com.joboffersapi.domain.userCRUD.dto.UserDto;
import com.joboffersapi.domain.userCRUD.dto.UserRegisterRequestDto;
import com.joboffersapi.domain.userCRUD.dto.UserResponseDto;
import com.joboffersapi.domain.userCRUD.exception.UserExistsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.joboffersapi.domain.userCRUD.UserMapper.mapFromUserToUserDto;

@Service
@AllArgsConstructor
class UserService {

    private final UserRepository userRepository;

    @Transactional
    UserResponseDto registerUser(UserRegisterRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.username())) throw new UserExistsException("Username " + requestDto.username() + " already exists");

        User toSave = User.builder()
                .username(requestDto.username())
                .email(requestDto.email())
                .password(requestDto.password())
                .build();
        User saved = userRepository.save(toSave);
        UserDto userDto = mapFromUserToUserDto(saved);
        return UserResponseDto.builder()
                .message("User registered successfully.")
                .userDto(userDto)
                .build();
    }

    UserDto findByUsername(final String username) {
        return mapFromUserToUserDto(userRepository.findByUsername(username));
    }

}
