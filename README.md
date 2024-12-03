# BlueJayDay

This is a repository for our weather dashboard. This is done by Jimmy Burke and Santiago Lizzaraga.

This application was developed on Spring Boot using Java, HTML, Javascript, and API utilization.

This code, once applied on a machine, can be ran using localhost, and will give weather descriptions for the city that the user inputs. It also shows a 5 day future forecast prediction, using our OpenWeatherMaps API.

![Capture](https://github.com/user-attachments/assets/3e7243d7-a84e-45dc-afa4-76413224724e)


While this application does not use a file system, it uses a:
MVC,
Directed Controller,
API utilization,
AI utilization,
Numerous java files,
Index HTML,
Application Controller (to initialize webpage)


In order for this application to work correctly, we had to implement a controller in order to launch our Gemini AI, as well as the MVC in total, to allow for the webpage to correctly output the information being taken from the API.

In order to work correctly, our Controller needed to be in this configuration to implement all aspects of our webpage.
```
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
```

Expected Results
<img width="1710" alt="Screenshot 2024-12-02 at 9 27 39 PM" src="https://github.com/user-attachments/assets/8689496e-a852-4e88-be98-23d274154637">

<img width="1710" alt="Screenshot 2024-12-02 at 9 28 56 PM" src="https://github.com/user-attachments/assets/7e0e8dd4-961e-4711-a408-be3f4a1ba598">
