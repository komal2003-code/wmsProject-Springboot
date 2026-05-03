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

    // 🔐 Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 🔐 Security Configuration
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // ❗ CSRF disable
            .csrf(csrf -> csrf.disable())

            // ❗ Authorization rules
            .authorizeHttpRequests(auth -> auth

                // 🔓 Public APIs
                .requestMatchers("/auth/**").permitAll()

                // ✅ GET products → anyone
                .requestMatchers(HttpMethod.GET, "/products/**").permitAll()

                // 🔐 ADMIN only for changes
                .requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")

                // 🔐 Other modules
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/operator/**").hasAnyRole("ADMIN", "OPERATOR")

                // बाकी सगळं secure
                .anyRequest().authenticated()
            )

            // 🔐 JWT Filter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}