package com.joboffersapi.domain.userCRUD;

import org.springframework.data.repository.Repository;

interface UserRepository extends Repository<User, Long> {

    User save(User user);

    User findByUsername(String username);

    boolean existsByUsername(String username);
}
