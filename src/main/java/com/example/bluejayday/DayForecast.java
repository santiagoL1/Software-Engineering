package com.example.bluejayday;

public class DayForecast {
    private String day;
    private String image;
    private String weatherType;
    private String temperature;

    // Constructor
    public DayForecast(String day, String image, String weatherType, String temperature) {
        this.day = day;
        this.image = image;
        this.weatherType = weatherType;
        this.temperature = temperature;
    }

    // Getters and Setters
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}

