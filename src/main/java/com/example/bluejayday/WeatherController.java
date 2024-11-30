package com.example.bluejayday;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WeatherController {

    @GetMapping("/")
    public String getWeatherData(Model model) {
        // Example values for dynamic content
        model.addAttribute("cityName", "New York");
        model.addAttribute("currentTemperature", "18°C");
        model.addAttribute("currentTime", "11:00 AM");
        model.addAttribute("currentDay", "Today");
        model.addAttribute("weatherType", "Cloudy");
        model.addAttribute("humidity", "72%");
        model.addAttribute("wind", "15 km/h");

        // 5-day forecast data
        List<DayForecast> forecast = List.of(
            new DayForecast("Fri", "rain.png", "Rain", "2°"),
            new DayForecast("Sat", "cloud.png", "Cloudy", "4°"),
            new DayForecast("Sun", "partly-cloudy.png", "Partly cloudy", "6°"),
            new DayForecast("Mon", "sun.png", "Sunny", "8°"),
            new DayForecast("Tue", "wind.png", "Windy", "5°")
        );
        model.addAttribute("forecast", forecast);

        return "index"; // Thymeleaf will render 'index.html'
    }
}

