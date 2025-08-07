package com.melo.bets.persistence;

import com.melo.bets.domain.User;
import com.melo.bets.domain.dto.UserDto;
import com.melo.bets.domain.dto.UserRegisterDto;
import com.melo.bets.domain.repository.IUserRepository;
import com.melo.bets.persistence.crud.UserCrudRepository;
import com.melo.bets.persistence.entity.UserEntity;
import com.melo.bets.persistence.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    private final UserCrudRepository userCrudRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserRepositoryImpl(UserCrudRepository userCrudRepository, UserMapper userMapper) {
        this.userCrudRepository = userCrudRepository;
        this.userMapper = userMapper;
    }


    @Override
    public List<UserDto> findAll() {
        List<UserEntity> users = userCrudRepository.findAll();
        return userMapper.toUserDtoList(users);
    }

    @Override
    public Optional<UserDto> findById(UUID id) {
        return userCrudRepository.findById(id).map(userMapper::toUserDto);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userCrudRepository.findByEmail(email).map(userMapper::toUser);
    }

    @Override
    public UserRegisterDto save(UserRegisterDto user) {
        UserEntity userEntity = userMapper.toUserRegisterDto(user);
        userEntity.setStatus(true);
        return userMapper.toUserRegisterDto(userCrudRepository.save(userEntity));
    }

    @Override
    public Optional <UserDto> update(UserDto user) {
        return null;
    }

    @Override
    public void delete(UUID id) {
        userCrudRepository.deleteById(id);
    }
}
