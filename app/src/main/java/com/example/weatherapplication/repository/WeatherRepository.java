package com.example.weatherapplication.repository;

import com.example.weatherapplication.Common.Common;
import com.example.weatherapplication.Helper.Helper;
import com.example.weatherapplication.Model.Weather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class WeatherRepository {

    public String fetchWeatherData(String latLng) {
        String stream = null;

        Helper http = new Helper();
        stream = http.getHTTPData(Common.API_LINK + latLng);

        return stream;
    }

    public Weather parseWeatherData(String jsonString) {
        Gson gson = new Gson();
        Type mType = new TypeToken<Weather>() {}.getType();
        return gson.fromJson(jsonString, mType);
    }

    public Weather fetchAndParseWeatherData(String latLng) {
        String jsonData = fetchWeatherData(latLng);
        return parseWeatherData(jsonData);
    }
}