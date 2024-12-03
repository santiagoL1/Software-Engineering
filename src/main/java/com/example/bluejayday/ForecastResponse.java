package com.example.bluejayday;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastResponse {
    private List<ForecastEntry> list;

    public List<ForecastEntry> getList() {
        return list;
    }

    public void setList(List<ForecastEntry> list) {
        this.list = list;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ForecastEntry {
    private Main main;
    private List<Weather> weather;
    private String dt_txt;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Main {
    private double temp;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Weather {
    private String description;
    private String icon;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}