package com.joboffersapi.domain.usercrud;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class UserConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public static UserFacade getUserFacadeForTests(UserRepository userRepository) {
        UserService userService = new UserService(userRepository, new BCryptPasswordEncoder());
        return new UserFacade(userService);
    }
}
