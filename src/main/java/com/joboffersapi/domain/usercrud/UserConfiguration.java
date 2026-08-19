package com.joboffersapi.domain.usercrud;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserConfiguration {

    @Bean
    public static UserFacade getUserFacadeForTests(UserRepository userRepository) {
        UserService userService = new UserService(userRepository);
        return new UserFacade(userService);
    }
}
