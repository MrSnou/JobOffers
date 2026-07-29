package com.joboffersapi.domain.usercrud;


class UserConfiguration {

    public static UserFacade getUserFacadeForTests(UserRepository userRepository) {

        UserService userService = new UserService(userRepository);
        return new UserFacade(userService);
    }
}
