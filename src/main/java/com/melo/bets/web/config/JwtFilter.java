package com.melo.bets.web.config;

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

        // 1️⃣ Validar que haya encabezado Authorization válido
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2️⃣ Extraer y validar el token JWT
        String jwt = authHeader.substring(7).trim(); // elimina el "Bearer "
        if (!jwtUtil.isValid(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3️⃣ Extraer datos del token (username y userId)
        String username = jwtUtil.getUsername(jwt);
        UUID userId = jwtUtil.getUserId(jwt); // 👈 nuevo claim personalizado

        // 4️⃣ Evitar reautenticar si ya hay contexto de seguridad
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Cargar el usuario desde tu servicio (verifica si existe)
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 5️⃣ Crear el token de autenticación
            UserDetailsWithId userDetailsWithId = new UserDetailsWithId(userDetails, userId);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetailsWithId, null, userDetails.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 6️⃣ Registrar en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authToken);

            // Opcional: imprimir el ID del usuario autenticado
            System.out.printf("🔐 Usuario autenticado: %s (id=%s)%n", username, userId);
        }

        // 7️⃣ Continuar con el flujo del filtro
        filterChain.doFilter(request, response);
    }
}
