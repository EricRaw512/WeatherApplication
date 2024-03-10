package com.example.weatherapplication.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapplication.Model.Weather;
import com.example.weatherapplication.repository.WeatherRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends ViewModel {
    private final MutableLiveData<Weather> weatherData = new MutableLiveData<>();
    private final WeatherRepository weatherRepository = new WeatherRepository();
    private final CompositeDisposable disposable = new CompositeDisposable();

    public LiveData<Weather> getWeatherData() {
        return weatherData;
    }

    public void fetchWeatherData(String latLng) {
        disposable.add(
                weatherRepository.fetchAndParseWeatherData(latLng)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::onWeatherDataFetched,
                                this::onError
                        )
        );
    }

    private void onWeatherDataFetched(Weather weather) {
        weatherData.setValue(weather);
    }

    private void onError(Throwable throwable) {
        // Handle the error
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear(); // Clear the CompositeDisposable when the ViewModel is cleared
    }
}
