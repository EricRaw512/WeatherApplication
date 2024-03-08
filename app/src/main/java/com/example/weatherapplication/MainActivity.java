package com.example.weatherapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.PackageManager;
import android.location.Location;
import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapplication.Common.Common;
import com.example.weatherapplication.Model.Weather;
import com.example.weatherapplication.ViewModel.WeatherViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private final int FINE_PERMISSION_CODE = 1;
    private LocationCallback locationCallback;
    private WeatherViewModel viewModel;

    TextView txtCity, txtLastUpdate, txtDescription, txtHumidity, txtTime, txtCelsius;
    ImageView imageView;
    static double lat, lng;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Control
//        txtCity = (TextView) findViewById(R.id.txtCity);
//        txtLastUpdate = (TextView) findViewById(R.id.txtLastUpdate);
//        txtDescription = (TextView) findViewById(R.id.txtDescription);
//        txtHumidity = (TextView) findViewById(R.id.txtHumidity);
//        txtTime = (TextView) findViewById(R.id.txtTime);
//        txtCelsius = (TextView) findViewById(R.id.txtCelsius);
//        imageView = (ImageView) findViewById(R.id.imageView);

        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        viewModel.getWeatherData().observe(this, weather -> {
            if (weather != null) {
                updateUI(weather);
            }
        });


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
            };
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


//        txtCity.setText(String.format("%s, %s,", openWeatherMap.getName(), openWeatherMap.getSys().getCountry()));
//        txtLastUpdate.setText(String.format("Last Updated: %s", Common.getDateNow()));
//        txtDescription.setText(String.format("%s", openWeatherMap.getWeathers().get(0).getDescription()));
//        txtHumidity.setText(String.format("%d%%", openWeatherMap.getMain().getHumidity()));
//        txtTime.setText(String.format("%s/%s", Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunrise()), Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunset())));
//        txtCelsius.setText(String.format("%.2f °C", openWeatherMap.getMain().getTemp()));
//        Picasso.get()
//                .load(Common.getImage(openWeatherMap.getWeathers().get(0).getIcon()))
//                .into(imageView);
    }
}