package com.example.weatherapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapplication.Adapters.HoursAdapter;
import com.example.weatherapplication.Common.Common;
import com.example.weatherapplication.Model.Days;
import com.example.weatherapplication.Model.Weather;
import com.example.weatherapplication.ViewModel.WeatherViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int FINE_PERMISSION_CODE = 1;
    private LocationCallback locationCallback;
    private WeatherViewModel viewModel;

    TextView txtDate, txtCondition, txtTemp, txtHighAndLowTemp, txtPrecipProb, txtPrecipType, txtWindSpeed, txtHumidity;
    ImageView weatherIcon, precipIcon, windIcon, humidityIcon;
    static double lat, lng;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        viewModel.getWeatherData().observe(this, weather -> {
            if (weather != null) {
                updateUI(weather);
            }
        });

        //set Context
        txtDate = (TextView) findViewById(R.id.textDate);
        txtCondition = (TextView) findViewById(R.id.textCondition);
        txtTemp = (TextView) findViewById(R.id.textTemp);
        txtHighAndLowTemp = (TextView) findViewById(R.id.textHighAndLowTemp);
        txtPrecipProb = (TextView) findViewById(R.id.textPrecipProb);
        txtPrecipType = (TextView) findViewById(R.id.textPrecipType);
        txtWindSpeed = (TextView) findViewById(R.id.textWindSpeed);
        txtHumidity = (TextView) findViewById(R.id.textHumidityPercen);
        weatherIcon = (ImageView) findViewById(R.id.weatherIcon);
        precipIcon = (ImageView) findViewById(R.id.precipIcon); 
        windIcon = (ImageView) findViewById(R.id.windIcon);
        humidityIcon = (ImageView) findViewById(R.id.humidityIcon);

        //Get Coordinates
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult.getLocations().isEmpty()) {
                    showLocationErrorMessage();
                    return;
                }

                Location location = locationResult.getLastLocation();
                lat = location.getLatitude();
                lng = location.getLongitude();
                currentLocation = location;

                String latLng = Common.apiRequest(String.valueOf(lat), String.valueOf(lng));
                viewModel.fetchWeatherData(latLng);
            }
        };

        checkPermissionAndGetLocation();
    }

    private void checkPermissionAndGetLocation() {
        if (checkPermission()) {
            getCurrentLocation();
        } else {
            requestLocationPermission();
        }
    }

    private void getCurrentLocation() {
        checkPermission();
        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener(location -> {
                    Log.d("MainActivity", "No location available");
                    if (location != null) {
                        // Update your UI with location data
                        currentLocation = location;
                        // Access latitude and longitude here
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        // ... (use latitude and longitude as needed)
                        String latLng = Common.apiRequest(String.valueOf(lat), String.valueOf(lng));
                        viewModel.fetchWeatherData(latLng);
                    } else {
                        showLocationErrorMessage();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("MainActivity", "Failed to get current location", e);
                    showLocationErrorMessage();
                }
        );
    }

    public void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE
        }, FINE_PERMISSION_CODE);
    }

    private void showPermissionDeniedMessage() {
        Toast.makeText(this, "Please allow the location permission", Toast.LENGTH_LONG).show();
    }

    private void showLocationErrorMessage() {
        Toast.makeText(this, "Failed to get current location", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop location updates when the app is being destroyed
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    public boolean checkPermission() {
        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                showPermissionDeniedMessage();
            }
        }
    }

    private void updateUI(Weather weather) {
        if (weather == null) return;

        setButton(weather);

        Days todayWeather = weather.getDays().get(0);
        txtDate.setText(Common.convertDateFormat(todayWeather.getDatetime()));
        txtCondition.setText(todayWeather.getConditions());
        txtTemp.setText(String.format("%s°C", todayWeather.getTemp()));
        txtHighAndLowTemp.setText(String.format("H:%s L:%s", todayWeather.getTempMax(), todayWeather.getTempMin()));
        txtPrecipProb.setText(String.format("%s%%", todayWeather.getPrecipProb()));
        txtPrecipType.setText(todayWeather.getPrecipType().get(0));
        txtWindSpeed.setText(String.format("%s Km/h", todayWeather.getWindSpeed()));
        txtHumidity.setText(String.format("%s%%", todayWeather.getHumidity()));
        weatherIcon.setImageResource(Common.getWeatherIcon(todayWeather.getIcon()));
        precipIcon.setImageResource(Common.getWeatherIcon("umbrella"));
        windIcon.setImageResource(Common.getWeatherIcon("wind"));
        humidityIcon.setImageResource(Common.getWeatherIcon("humidity"));

        RecyclerView recyclerView = findViewById(R.id.view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecyclerView.Adapter adapterHours = new HoursAdapter(Common.listOfHoursForecast(weather));
        recyclerView.setAdapter(adapterHours);
    }

    private void setButton(Weather weather) {
        TextView next7DayBtn = findViewById(R.id.nxt7DaysBtn);
        next7DayBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TomorrowActivity.class);
            ArrayList<Days> daysList = new ArrayList<>(weather.getDays());
            intent.putParcelableArrayListExtra("daysList", daysList);
            startActivity(intent);
        });
    }
}