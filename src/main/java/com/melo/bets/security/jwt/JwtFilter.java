package com.melo.bets.security.jwt;

import com.melo.bets.security.user.UserDetailsWithId;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Validate Authorization header exists and is valid
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract and validate JWT token
        String jwt = authHeader.substring(7).trim(); // removes "Bearer "
        if (!jwtUtil.isValid(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract data from token (username and userId)
        String username = jwtUtil.getUsername(jwt);
        UUID userId = jwtUtil.getUserId(jwt); // custom claim

        // Avoid re-authenticating if security context already exists
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load user from service (verify it exists)
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Create authentication token
            UserDetailsWithId userDetailsWithId = new UserDetailsWithId(userDetails, userId);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetailsWithId, null, userDetails.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Register in security context
            SecurityContextHolder.getContext().setAuthentication(authToken);

            // Optional: print authenticated user ID
            System.out.printf("🔐 Authenticated user: %s (id=%s)%n", username, userId);
        }

        // Continue with filter chain
        filterChain.doFilter(request, response);
    }
}
