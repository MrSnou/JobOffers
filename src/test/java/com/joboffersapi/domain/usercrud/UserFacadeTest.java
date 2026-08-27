package com.joboffersapi.domain.usercrud;

import com.joboffersapi.domain.usercrud.dto.UserDto;
import com.joboffersapi.infrastructure.usercrud.dto.UserRegisterRequestDto;
import com.joboffersapi.domain.usercrud.dto.UserResponseDto;
import com.joboffersapi.domain.usercrud.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.joboffersapi.domain.usercrud.UserConfiguration.getUserFacadeForTests;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class UserFacadeTest {

    UserFacade userFacade = getUserFacadeForTests(
            new InMemoryUserRepository()
    );

    private static final String DEFAULT_USER_NAME = "TestUser";
    private static final String DEFAULT_USER_PASSWORD = "TestPassword";

    static User aUser() {
        return User.builder()

                .build();
    }

    static User aUser(String username) {
        return User.builder()

                .build();
    }

    @Nested
    @DisplayName("register - Tests")
    class RegisterTests {
        @Test
        @DisplayName("Should return UserResponseDto with message and userDto.")
        public void should_return_OfferResponseDto_with_message_and_offerDto() {
            // Given

            // When

            // Then

        }
    }

    @Nested
    @DisplayName("findUserByUsername - Tests")
    class FindUserByUsernameTests {

        @Test
        @DisplayName("Should throw UserNotFoundException when user with given username does not exist.")
        public void should_throw_UserNotFoundException_when_user_with_given_username_does_not_exist() {
            // Given

            // When

            // Then

        }


        @Test
        @DisplayName("Should return userDto.")
        public void should_return_OfferResponseDto_with_message_and_offerDto() {
            // Given

            // When

            // Then
        }
    }
}
