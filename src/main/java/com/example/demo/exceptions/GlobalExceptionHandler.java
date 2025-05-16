
package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ModelAndView handleUserExists(UserAlreadyExistsException ex) {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("error", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView handleUsernameNotFound(UsernameNotFoundException ex) {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("error", "Invalid credentials");
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneric(Exception ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", "Something went wrong: " + ex.getMessage());
        return mav;
    }
}
