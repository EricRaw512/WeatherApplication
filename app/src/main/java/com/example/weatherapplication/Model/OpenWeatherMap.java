package com.example.weatherapplication.Model;

import java.util.List;

public class OpenWeatherMap {
    private Clouds clouds;
    private List<Weather> weathers;
    private String base;
    private Main main;
    private Wind wind;
    private Rain rain;
    private int dt;
    private Sys sys;
    private int id;
    private String name;
    private int cod;

    public OpenWeatherMap() {
    }

    public OpenWeatherMap(Clouds clouds, List<Weather> weathers, String base, Main main, Wind wind, Rain rain, int dt, Sys sys, int id, String name, int cod) {
        this.clouds = clouds;
        this.weathers = weathers;
        this.base = base;
        this.main = main;
        this.wind = wind;
        this.rain = rain;
        this.dt = dt;
        this.sys = sys;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public List<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
}
