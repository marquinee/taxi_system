package com.example.taxi_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration

public class SecurityConfig {

    private final CustomSuccessHandler successHandler;

    public SecurityConfig(CustomSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/about", "/error/**").permitAll() // Доступ без авторизации
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Только для ADMIN
                        .requestMatchers("/operator/**").hasRole("OPERATOR") // Только для OPERATOR
                        .requestMatchers("/driver/**").hasRole("DRIVER") // Только для DRIVER
                        .anyRequest().authenticated() // Все остальные URL требуют авторизации
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(successHandler) // Перенаправление после успешного входа
                        .permitAll() // Страница логина доступна всем
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout") // Перенаправление после выхода
                        .permitAll()
                );

        return http.build();
    }
}

