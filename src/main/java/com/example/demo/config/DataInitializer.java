//package com.example.demo.config;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import com.example.demo.model.User;
//import com.example.demo.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Example users
//        createUserIfNotExists("dog@example.com", "dog", "password62114");
//        createUserIfNotExists("catsss@example.com", "cat", "password72143");
//    }
//
//    private void createUserIfNotExists(String email, String name, String password) {
//        if (!userRepository.existsByEmail(email)) {
//            User user = new User(null, name, email, new BCryptPasswordEncoder().encode(password));
//            userRepository.save(user);
//        }
//    }
//
//
//}
