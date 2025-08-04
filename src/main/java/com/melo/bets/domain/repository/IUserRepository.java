package com.melo.bets.domain.repository;

import com.melo.bets.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository {

    List<User> findAll();

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    User save(User user);

    Optional <User> update(User user);

    void delete(UUID id);
}
