package com.example.demo.config;

import com.example.demo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomLoginSuccessHandler successHandler;  // Inject the custom success handler

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signup", "/login", "/css/**", "/js/**").permitAll()  // Allow public access
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // Only admin can access /admin/**
                        .requestMatchers("/chat/**").hasAnyRole("USER", "ADMIN")  // Both user and admin can access chat
                        .anyRequest().authenticated()  // All other requests require authentication
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Custom login page
                        .successHandler(successHandler)  // Use custom success handler
                        .failureUrl("/login?error=true")  // Failure URL with error parameter
                        .failureHandler(authenticationFailureHandler())  // Apply custom failure handler
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")  // Redirect to login page after logout
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());  // Disable CSRF for simplicity

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Use BCrypt for secure password encoding
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();  // Authentication manager for managing user authentication
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);  // Corrected method name
        provider.setPasswordEncoder(passwordEncoder());  // Set password encoder for secure authentication
        return provider;
    }



    // Custom authentication failure handler
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            // Store error message in session for feedback
            request.getSession().setAttribute("error", "Invalid username or password");
            // Redirect to login page with an error query parameter
            response.sendRedirect("/login?error=true");
        };
    }
}





