package com.melo.bets.domain.service;

import com.melo.bets.domain.dto.user.LoginDto;
import com.melo.bets.domain.exception.custom.InvalidCredentialsException;
import com.melo.bets.web.config.JwtUtil;
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

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public String authenticate(LoginDto loginDto) {
        try {
            UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(), loginDto.getPassword());

            Authentication authentication = this.authenticationManager.authenticate(login);

            return this.jwtUtil.createToken(loginDto.getEmail());
        } catch (
                BadCredentialsException |
                InvalidCredentialsException e) {
            throw new InvalidCredentialsException();
        }
    }
}
