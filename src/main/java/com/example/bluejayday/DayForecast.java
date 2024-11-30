package com.example.bluejayday;

public class DayForecast {
    private String name;
    private String icon;
    private String weather;
    private String temperature;

    public DayForecast(String name, String icon, String weather, String temperature) {
        this.name = name;
        this.icon = icon;
        this.weather = weather;
        this.temperature = temperature;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getWeather() {
        return weather;
    }

    public String getTemperature() {
        return temperature;
    }
}

