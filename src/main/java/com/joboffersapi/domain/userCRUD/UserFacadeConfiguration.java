package com.joboffersapi.domain.userCRUD;


class UserFacadeConfiguration {

    public static UserFacade getUserFacadeForTests(UserRepository userRepository) {

        UserService userService = new UserService(userRepository);
        return new UserFacade(userService);
    }
}
