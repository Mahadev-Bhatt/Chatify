package com.example.demo.config;



import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CustomLoginSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        logger.info("CustomLoginSuccessHandler invoked for user: {}", authentication.getName());

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        logger.info("Is admin: {}", isAdmin);

        if (isAdmin) {
            logger.info("Redirecting to: /admin/dashboard");
            response.sendRedirect("/admin/dashboard");
        } else {
            logger.info("Redirecting to: /chat");
            response.sendRedirect("/chat");
        }
    }
}
