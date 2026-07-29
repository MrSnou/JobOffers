package com.joboffersapi.domain.usercrud;

import com.joboffersapi.domain.usercrud.dto.UserDto;

class UserMapper {

    /**
     * Maps a User to a UserDto object.
     * @param user the User to map.
     * @return the mapped UserDto object.
     */

    static UserDto mapFromUserToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    /**
     * Maps a UserDto to a User object.
     * Note: The password field is not included in the UserDto, so it will not be set in the User object.
     *
     * @mapped User object with ID, USERNAME, EMAIL rest of the fields have null value!
     * @param userDto the UserDto to map.
     * @return the mapped User object.
     */
    static User mapFromUserDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.id())
                .username(userDto.username())
                .email(userDto.email())
                .build();
    }
}
