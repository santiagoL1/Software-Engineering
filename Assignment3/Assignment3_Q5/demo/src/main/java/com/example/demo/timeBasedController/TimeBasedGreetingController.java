package com.example.demo.timeBasedController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalTime;

@Controller
public class TimeBasedGreetingController {

    @GetMapping("/")
    public String displayGreeting(Model model) {
        // Get the current time
        LocalTime currentTime = LocalTime.now();
        String timeOfDay;

        // Determine time of day
        if (currentTime.isBefore(LocalTime.NOON)) {
            timeOfDay = "Morning";
        } else if (currentTime.isBefore(LocalTime.of(18, 0))) {
            timeOfDay = "Afternoon";
        } else {
            timeOfDay = "Evening";
        }

        // Add the time of day to the model
        model.addAttribute("timeOfDay", timeOfDay);

        // Return the name of the Thymeleaf template
        return "timeBasedGreeting";
    }
}

