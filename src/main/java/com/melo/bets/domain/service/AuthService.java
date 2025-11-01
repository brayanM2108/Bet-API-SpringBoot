package com.melo.bets.domain.service;

import com.melo.bets.domain.dto.user.LoginDto;
import com.melo.bets.domain.exception.user.InvalidCredentialsException;
import com.melo.bets.domain.repository.IUserRepository;
import com.melo.bets.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final IUserRepository userRepository;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, IUserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public String authenticate(LoginDto loginDto) {
        try {
            UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(), loginDto.getPassword());

            Authentication authentication = this.authenticationManager.authenticate(login);

            LoginDto user = this.userRepository.findByEmail(loginDto.getEmail())
                    .orElseThrow(InvalidCredentialsException::new);

            // 3️⃣ Creamos el token con email + UUID como claim
            return this.jwtUtil.createToken(user.getEmail(), user.getId());

        } catch (
                BadCredentialsException |
                InvalidCredentialsException e) {
            throw new InvalidCredentialsException();
        }
    }
}
