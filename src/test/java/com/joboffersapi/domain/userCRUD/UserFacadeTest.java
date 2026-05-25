package com.joboffersapi.domain.userCRUD;

import com.joboffersapi.domain.userCRUD.dto.UserDto;
import com.joboffersapi.domain.userCRUD.dto.UserRegisterRequestDto;
import com.joboffersapi.domain.userCRUD.dto.UserResponseDto;
import com.joboffersapi.domain.userCRUD.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.joboffersapi.domain.userCRUD.UserFacadeConfiguration.getUserFacadeForTests;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class UserFacadeTest {

    UserFacade userFacade = getUserFacadeForTests(
            new InMemoryUserRepository()
    );

    private static final String DEFAULT_USER_NAME = "TestUser";
    private static final String DEFAULT_USER_PASSWORD = "TestPassword";
    private static final String DEFAULT_USER_EMAIL = "UserEmail@Test.com";

    static User aUser() {
        return User.builder()
                .username(DEFAULT_USER_NAME)
                .password(DEFAULT_USER_PASSWORD)
                .email(DEFAULT_USER_EMAIL)
                .build();
    }

    static User aUser(String username) {
        return User.builder()
                .username(username)
                .password(DEFAULT_USER_PASSWORD)
                .email(DEFAULT_USER_EMAIL)
                .build();
    }

    @Nested
    @DisplayName("register - Tests")
    class RegisterTests {
        @Test
        @DisplayName("Should return UserResponseDto with message and userDto.")
        public void should_return_OfferResponseDto_with_message_and_offerDto() {
            // Given
            UserRegisterRequestDto userRegisterRequestDto = UserRegisterRequestDto.builder()
                    .username(DEFAULT_USER_NAME)
                    .password(DEFAULT_USER_PASSWORD)
                    .email(DEFAULT_USER_EMAIL)
                    .build();
            // When
            UserResponseDto registerResponse = userFacade.register(userRegisterRequestDto);
            // Then
            assertThat(registerResponse).extracting("message", "userDto").containsExactly(
                    "User registered successfully.",
                    UserDto.builder()
                            .id(0L)
                            .username(DEFAULT_USER_NAME)
                            .email(DEFAULT_USER_EMAIL)
                            .build()
            );
        }
    }

    @Nested
    @DisplayName("findUserByUsername - Tests")
    class FindUserByUsernameTests {

        @Test
        @DisplayName("Should throw UserNotFoundException when user with given username does not exist.")
        public void should_throw_UserNotFoundException_when_user_with_given_username_does_not_exist() {
            // Given
            String nonExistentUsername = "NonExistentUser";
            // When
            Throwable thrown = org.assertj.core.api.AssertionsForClassTypes.catchThrowable(() -> userFacade.findUserByUsername(nonExistentUsername));
            // Then
            assertThat(thrown).isInstanceOf(UserNotFoundException.class)
                    .hasMessage("User with username " + nonExistentUsername + " not found.");
        }


        @Test
        @DisplayName("Should return userDto.")
        public void should_return_OfferResponseDto_with_message_and_offerDto() {
            // Given
            UserRegisterRequestDto userRegisterRequestDto = UserRegisterRequestDto.builder()
                    .username(DEFAULT_USER_NAME)
                    .password(DEFAULT_USER_PASSWORD)
                    .email(DEFAULT_USER_EMAIL)
                    .build();
            userFacade.register(userRegisterRequestDto);
            // When
            UserDto userByUsername = userFacade.findUserByUsername(DEFAULT_USER_NAME);
            // Then
            assertThat(userByUsername).extracting("id", "username", "email").containsExactly(
                    0L,
                    DEFAULT_USER_NAME,
                    DEFAULT_USER_EMAIL
            );
        }
    }
}
