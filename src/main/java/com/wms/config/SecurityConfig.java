package com.wms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http

            // Disable CSRF
            .csrf(csrf -> csrf.disable())

            // Stateless Session
            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    )
            )

            // Authorization Rules
            .authorizeHttpRequests(auth -> auth

                    // PUBLIC APIs
                    .requestMatchers("/auth/**").permitAll()

                    // Barcode Images Public
                    .requestMatchers("/barcodes/**").permitAll()

                    // Error Page Public
                    .requestMatchers("/error").permitAll()

                    // =========================
                    // PRODUCTS APIs
                    // =========================

                    // GET -> ADMIN + OPERATOR
                    .requestMatchers(
                            HttpMethod.GET,
                            "/products/**"
                    ).hasAnyAuthority(
                            "ROLE_ADMIN",
                            "ROLE_OPERATOR"
                    )

                    // POST -> ADMIN only
                    .requestMatchers(
                            HttpMethod.POST,
                            "/products/**"
                    ).hasAuthority("ROLE_ADMIN")

                    // PUT -> ADMIN only
                    .requestMatchers(
                            HttpMethod.PUT,
                            "/products/**"
                    ).hasAuthority("ROLE_ADMIN")

                    // DELETE -> ADMIN only
                    .requestMatchers(
                            HttpMethod.DELETE,
                            "/products/**"
                    ).hasAuthority("ROLE_ADMIN")

                    // Any Other API
                    .anyRequest().authenticated()
            )

            // JWT Filter
            .addFilterBefore(
                    jwtFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}