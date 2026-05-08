package com.wms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

// ✅ IMPORTANT IMPORT
import org.springframework.http.HttpMethod;
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            // 🔥 IMPORTANT FIX
            .sessionManagement(session -> session.sessionCreationPolicy(
                    org.springframework.security.config.http.SessionCreationPolicy.STATELESS
            ))

            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/barcodes/**").permitAll()
            		.requestMatchers(HttpMethod.GET, "/barcodes/**").permitAll()
                .requestMatchers("/auth/**").permitAll()

                .requestMatchers(HttpMethod.GET, "/products/**").permitAll()

                .requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")

                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/operator/**").hasAnyRole("ADMIN", "OPERATOR")
                //.requestMatchers("/barcodes/**").permitAll()
                .anyRequest().authenticated()
            )

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

