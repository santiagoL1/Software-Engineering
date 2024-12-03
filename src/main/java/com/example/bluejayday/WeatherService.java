package com.example.bluejayday;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    private static final String API_KEY = "d4f2c486e541abf1beabcf1eae8b8053"; // Replace with your API key
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";

    public List<DayForecast> get5DayForecast(String city) {
        // Build the API URL
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("q", city)
                .queryParam("units", "metric") // For Celsius
                .queryParam("appid", API_KEY)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        try {
            // Make the API request
            ForecastResponse response = restTemplate.getForObject(url, ForecastResponse.class);

            // Parse and return the forecast data
            if (response != null && response.getList() != null) {
                return parseForecast(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle API errors or fallback to defaults
        }
        return new ArrayList<>();
    }

    private List<DayForecast> parseForecast(ForecastResponse response) {
        List<DayForecast> forecasts = new ArrayList<>();

        for (int i = 0; i < response.getList().size(); i += 8) { // Every 8 entries ≈ daily forecast
            ForecastEntry entry = response.getList().get(i);
            String day = entry.getDt_txt().split(" ")[0]; // Extract the day
            String icon = "http://openweathermap.org/img/wn/" + entry.getWeather().get(0).getIcon() + ".png"; // Icon URL
            String weather = entry.getWeather().get(0).getDescription();
            String temp = entry.getMain().getTemp() + "°C";

            forecasts.add(new DayForecast(day, icon, weather, temp));
        }
        return forecasts;
    }
}