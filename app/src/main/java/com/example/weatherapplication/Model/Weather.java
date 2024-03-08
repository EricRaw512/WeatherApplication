package com.example.weatherapplication.Model;

import java.util.List;

public class Weather {

    private String address;
    private List<Days> days;

    public Weather(String address, List<Days> days) {
        this.address = address;
        this.days = days;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Days> getDays() {
        return days;
    }

    public void setDays(List<Days> days) {
        this.days = days;
    }
}
