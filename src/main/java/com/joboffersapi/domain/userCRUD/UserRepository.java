package com.joboffersapi.domain.userCRUD;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface UserRepository extends Repository<User, Long> {

    User save(User user);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
