package com.example.weatherapplication.repository;

import com.example.weatherapplication.Common.Common;
import com.example.weatherapplication.Helper.Helper;
import com.example.weatherapplication.Model.Weather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class WeatherRepository {

    public Observable<String> fetchWeatherData(String latLng) {
        return Observable.fromCallable(() -> {
            Helper http = new Helper();
            return http.getHTTPData(Common.API_LINK + latLng);
        }).subscribeOn(Schedulers.io());
    }

    public Weather parseWeatherData(String jsonString) {
        Gson gson = new Gson();
        Type mType = new TypeToken<Weather>() {}.getType();
        return gson.fromJson(jsonString, mType);
    }

    public Observable<Weather> fetchAndParseWeatherData(String latLng) {
        return fetchWeatherData(latLng)
                .map(this::parseWeatherData)
                .onErrorReturn(throwable -> {
                    // Handle error, e.g., log it or return a default Weather object
                    return new Weather(); // Return a default Weather object or null
                });
    }
}