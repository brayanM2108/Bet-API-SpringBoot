package com.melo.bets.persistence;

import com.melo.bets.domain.User;
import com.melo.bets.domain.repository.IUserRepository;
import com.melo.bets.persistence.crud.UserCrudRepository;
import com.melo.bets.persistence.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository implements IUserRepository {

    private final UserCrudRepository userCrudRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserRepository(UserCrudRepository userCrudRepository, UserMapper userMapper) {
        this.userCrudRepository = userCrudRepository;
        this.userMapper = userMapper;
    }


    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Optional <User> update(User user) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
