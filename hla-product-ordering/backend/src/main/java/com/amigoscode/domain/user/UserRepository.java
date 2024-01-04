package com.amigoscode.domain.user;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    void update(User user);

    void remove(Integer id);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer id);

    PageUser findAllByName(String userName, Pageable pageable);

}