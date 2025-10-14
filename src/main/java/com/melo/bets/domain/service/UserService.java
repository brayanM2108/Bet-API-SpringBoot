package com.melo.bets.domain.service;

import com.melo.bets.domain.dto.user.LoginDto;
import com.melo.bets.domain.dto.user.UserBalanceDto;
import com.melo.bets.domain.dto.user.UserDto;
import com.melo.bets.domain.dto.user.UserRegisterDto;
import com.melo.bets.domain.exception.custom.DocumentAlreadyExistsException;
import com.melo.bets.domain.exception.custom.EmailAlreadyExistsException;
import com.melo.bets.domain.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
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

    public Optional<UserBalanceDto> getBalance(UUID id) {
        return userRepository.findBalance(id);
    }

    @Transactional
    public void updateBalance(UserBalanceDto userBalance) {
        userRepository.updateBalance(userBalance.id(), userBalance.balance());
    }

    public UserRegisterDto save(UserRegisterDto user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new EmailAlreadyExistsException(user.getEmail());
        }
        if (userRepository.existByDocument(user.getDocument())) {
            throw new DocumentAlreadyExistsException(user.getDocument());
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
