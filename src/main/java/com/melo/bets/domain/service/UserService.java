package com.melo.bets.domain.service;

import com.melo.bets.domain.dto.user.LoginDto;
import com.melo.bets.domain.dto.user.UserDto;
import com.melo.bets.domain.dto.user.UserRegisterDto;
import com.melo.bets.persistence.UserRepositoryImpl;
import com.melo.bets.persistence.crud.UserCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepositoryImpl userRepository;
    private final UserCrudRepository userCrudRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepositoryImpl userRepository, UserCrudRepository userCrudRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userCrudRepository = userCrudRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<UserDto> getAll() {
        return userRepository.findAll();
    }


    public Optional<UserDto> getById(UUID id) {
        return userRepository.findById(id);
    }


    public Optional<LoginDto> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public UserRegisterDto save(UserRegisterDto user) {
        if (userCrudRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }


    public boolean delete(UUID id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(id);
                    return true;
                })
                .orElse(false);
    }


}
