package com.example.demo.Controller;

import com.example.demo.model.Role;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // Show Sign-Up page
    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup";  // This returns the sign-up form
    }

    // Handle Sign-Up form submission
    @PostMapping("/signup")
    public String handleSignup(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("confirmPassword") String confirmPassword,
                               Model model,
                               HttpServletRequest request) { // Inject HttpServletRequest
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "signup";
        }
        try {
            Role userRole = Role.USER; // Default role is USER
            userService.registerUser(email, password, userRole);
            logger.info("User registered successfully: {}", email);

            // Automatically log in the user after successful registration
            UserDetails userDetails = userService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("Authentication set in SecurityContextHolder for: {}", email);
            logger.info("Current authentication: {}", SecurityContextHolder.getContext().getAuthentication());

            // Redirect to the chat page directly after successful registration and login
            return "redirect:/chat";

        } catch (Exception e) {
            logger.error("Error during registration: {}", e.getMessage(), e);
            model.addAttribute("error", "Error occurred during registration: " + e.getMessage());
            return "signup";
        }
    }
}





