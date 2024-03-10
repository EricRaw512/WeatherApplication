package com.example.weatherapplication.Model;

import java.util.List;

public class Weather {

    private String address;
    private List<Days> days;

    public Weather() {
    }

    public List<Days> getDays() {
        return days;
    }

    public void setDays(List<Days> days) {
        this.days = days;
    }
}
