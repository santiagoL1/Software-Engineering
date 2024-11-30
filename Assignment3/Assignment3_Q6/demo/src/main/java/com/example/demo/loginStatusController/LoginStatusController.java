package com.example.demo.loginStatusController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginStatusController {

    // Method to handle root URL
    @GetMapping("/")
    public String manageLoginStatus(Model model) {
        // Simulate user login status (set this to true or false)
        model.addAttribute("isLoggedIn", false);  // Change to false to test the alternate message
        return "index";  // Return the index.html template
    }
}




