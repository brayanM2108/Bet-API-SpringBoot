package com.melo.bets.domain.service;

import com.melo.bets.domain.UserRole;
import com.melo.bets.domain.dto.user.LoginDto;
import com.melo.bets.domain.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class UserSecurityService implements UserDetailsService {

    private final IUserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserSecurityService.class);
    public UserSecurityService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LoginDto user = this.userRepository.findByEmail(email).
                orElseThrow(()-> new UsernameNotFoundException("User not found with email: " + email));

        String[] roles = user.getRoles().stream().map(UserRole::getRole).toArray(String[]::new);

        logger.info("Usuario {} esta intentando autenticarse con roles: {}", email, Arrays.toString(roles));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .disabled(!user.getStatus())
                .roles(roles)
                .build();
    }

    //ASIGNAR GRANTED AUTHORITIES
    private String[] getAuthorities(String roles){

        return new String[] {};
    }
    private List<GrantedAuthority> grantedAuthorities(String [] roles) {
        List<GrantedAuthority> authorities = new ArrayList<>(roles.length);

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

            for (String authority : this.getAuthorities(role)) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }
        return authorities;
    }

    int numero = 42;


}
