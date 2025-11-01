package com.melo.bets.infrastructure.persistence;

import com.melo.bets.domain.dto.user.LoginDto;
import com.melo.bets.domain.dto.user.UserBalanceDto;
import com.melo.bets.domain.dto.user.UserDto;
import com.melo.bets.domain.dto.user.UserRegisterDto;
import com.melo.bets.domain.exception.user.UserNotFoundException;
import com.melo.bets.domain.repository.IUserRepository;
import com.melo.bets.infrastructure.persistence.crud.UserCrudRepository;
import com.melo.bets.infrastructure.persistence.entity.UserEntity;
import com.melo.bets.infrastructure.persistence.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    public Page<UserDto> findAll(Pageable pageable) {
        Page<UserEntity> users = userCrudRepository.findAll(pageable);
        return users.map(userMapper::toUserDto);
    }

    @Override
    public Optional<UserDto> findById(UUID id) {
        if (userCrudRepository.findById(id).isEmpty()) throw new UserNotFoundException(id);
        return userCrudRepository.findById(id).map(userMapper::toUserDto);
    }

    @Override
    public Optional<LoginDto> findByEmail(String email) {
        return userCrudRepository.findByEmail(email).map(userMapper::toLoginDto);
    }

    @Override
    public boolean existByDocument(String document) {
        return userCrudRepository.existsByDocument(document);
    }


    @Override
    public Optional<UserBalanceDto> findBalance(UUID id) {
        return userCrudRepository.findBalanceByUserId(id);
    }

    @Modifying
    @Transactional
    @Override
    public void updateBalance(UUID id, BigDecimal balance) {
        userCrudRepository.updateBalance(id, balance);
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
