package com.example.weatherapplication.Model;

public class Hours {

    private String datetime;
    private double temp;
    private String icon;

    public Hours(String datetime, double temp, String icon) {
        this.datetime = datetime;
        this.temp = temp;
        this.icon = icon;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
