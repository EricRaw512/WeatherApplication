package com.example.weatherapplication.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapplication.Model.Weather;
import com.example.weatherapplication.repository.WeatherRepository;

public class WeatherViewModel extends ViewModel {
    private final MutableLiveData<Weather> weatherData = new MutableLiveData<>();
    private final WeatherRepository weatherRepository = new WeatherRepository();

    public LiveData<Weather> getWeatherData() {
        return weatherData;
    }

    public void fetchWeatherData(String latLng) {
        // Fetch weather data from the repository and post the result to the LiveData
        weatherData.setValue(weatherRepository.fetchAndParseWeatherData(latLng));
    }
}
