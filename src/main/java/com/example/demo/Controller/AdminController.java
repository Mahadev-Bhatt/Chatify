package com.example.demo.Controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;


import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;


    @GetMapping("/admin/dashboard")
    public String showRegisteredUser() {
        return "admin-dashboard"; // This will map to registered-user.html
    }

    // Admin view to show registered users @GetMapping("/registered-user")
    //    public String showRegisteredUser() {
    //        return "registered-user"; // This will map to registered-user.html
    //    }
}



