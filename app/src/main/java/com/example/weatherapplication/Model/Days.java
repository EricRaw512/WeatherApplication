package com.example.weatherapplication.Model;

import java.util.List;

public class Days {

    private String datetime;
    private double temp;
    private double humidity;
    private double precipProb;
    private List<String> precipType;
    private double windSpeed;
    private String conditions;
    private String icon;
    private List<Hours> hours;

    public Days(String datetime, double temp, double humidity, double precipProb, List<String> precipType, double windSpeed, String conditions, String icon, List<Hours> hours) {
        this.datetime = datetime;
        this.temp = temp;
        this.humidity = humidity;
        this.precipProb = precipProb;
        this.precipType = precipType;
        this.windSpeed = windSpeed;
        this.conditions = conditions;
        this.icon = icon;
        this.hours = hours;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPrecipProb() {
        return precipProb;
    }

    public void setPrecipProb(double precipProb) {
        this.precipProb = precipProb;
    }

    public List<String> getPrecipType() {
        return precipType;
    }

    public void setPrecipType(List<String> precipType) {
        this.precipType = precipType;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Hours> getHours() {
        return hours;
    }

    public void setHours(List<Hours> hours) {
        this.hours = hours;
    }
}
