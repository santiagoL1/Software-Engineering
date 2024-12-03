package com.example.bluejayday;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WeatherController {

    private final GeminiService geminiService;

    // Constructor injection without @Autowired (Spring automatically detects it)
    public WeatherController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/")
    public String getWeatherData(Model model) {
        // Sample static data for the weather
        model.addAttribute("cityName", "New York");
        model.addAttribute("currentTemperature", "18°C");
        model.addAttribute("currentTime", "11:00 AM");
        model.addAttribute("currentDay", "Today");
        model.addAttribute("weatherType", "Cloudy");
        model.addAttribute("humidity", "72%");
        model.addAttribute("wind", "15 km/h");

        // Initialize the list of DayForecast objects
        List<DayForecast> forecast = List.of(
            new DayForecast("Fri", "rain.png", "Rain", "2°"),
            new DayForecast("Sat", "cloud.png", "Cloudy", "4°"),
            new DayForecast("Sun", "partly-cloudy.png", "Partly cloudy", "6°"),
            new DayForecast("Mon", "sun.png", "Sunny", "8°"),
            new DayForecast("Tue", "wind.png", "Windy", "5°")
        );
        model.addAttribute("forecast", forecast);

        return "index";
    }

    @GetMapping("/generate")
    public String generateResponse(@RequestParam("input") String input, Model model) {
        // Call the Gemini API service to get the response
        String geminiResponse = geminiService.getGeminiResponse(input);
        // Add the Gemini response to the model to display it on the webpage
        model.addAttribute("geminiResponse", geminiResponse);
        
        // Return the index page to display the result
        return "index";
    }
}







