package com.example.weatherapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapplication.Common.Common;
import com.example.weatherapplication.Helper.Helper;
import com.example.weatherapplication.Model.OpenWeatherMap;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private final int FINE_PERMISSION_CODE = 1;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    TextView txtCity, txtLastUpdate, txtDescription, txtHumidity, txtTime, txtCelsius;
    ImageView imageView;
    static double lat, lng;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Control
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtLastUpdate = (TextView) findViewById(R.id.txtLastUpdate);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtHumidity = (TextView) findViewById(R.id.txtHumidity);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtCelsius = (TextView) findViewById(R.id.txtCelsius);
        imageView = (ImageView) findViewById(R.id.imageView);


        //Get Coordinates
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }

    private void getLastLocation() {
        checkPermission();
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                currentLocation = location;
            }
        });

        lat = currentLocation.getLatitude();
        lng = currentLocation.getLongitude();

        new GetWeather().execute(Common.apiRequest(String.valueOf(lat), String.valueOf(lng)));
    }

    public void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE
            }, FINE_PERMISSION_CODE);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Please allow the location permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startLocationUpdates() {
        checkPermission();
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    private class GetWeather extends AsyncTaskExecutorService<String, String, String> {
        ProgressBar pb = new ProgressBar(MainActivity.this);

        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String params) {
            String stream = null;

            Helper http = new Helper();
            stream = http.getHTTPData(params);
            return stream;
        }

        @SuppressLint("DefaultLocale")
        @Override
        protected void onPostExecute(String s) {
            if (s.contains("Error: Not found city")) {
                pb.setVisibility(View.GONE);
                return;
            }

            Gson gson = new Gson();
            Type mType = new TypeToken<OpenWeatherMap> () {}.getType();
            openWeatherMap = gson.fromJson(s, mType);
            pb.setVisibility(View.GONE);

            txtCity.setText(String.format("%s, %s,", openWeatherMap.getName(), openWeatherMap.getSys().getCountry()));
            txtLastUpdate.setText(String.format("Last Updated: %s", Common.getDateNow()));
            txtDescription.setText(String.format("%s", openWeatherMap.getWeathers().get(0).getDescription()));
            txtHumidity.setText(String.format("%d%%", openWeatherMap.getMain().getHumidity()));
            txtTime.setText(String.format("%s/%s", Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunrise()), Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunset())));
            txtCelsius.setText(String.format("%.2f °C", openWeatherMap.getMain().getTemp()));
            Picasso.get()
                    .load(Common.getImage(openWeatherMap.getWeathers().get(0).getIcon()))
                    .into(imageView);
        }
    }

    public static abstract class AsyncTaskExecutorService < Params, Progress, Result > {

        private final ExecutorService executor;
        private Handler handler;

        protected AsyncTaskExecutorService() {
            executor = Executors.newSingleThreadExecutor(r -> {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            });

        }

        public ExecutorService getExecutor() {
            return executor;
        }

        public Handler getHandler() {
            if (handler == null) {
                synchronized(AsyncTaskExecutorService.class) {
                    handler = new Handler(Looper.getMainLooper());
                }
            }
            return handler;
        }

        protected void onPreExecute() {
            // Override this method wherever you want to perform task before background execution get started
        }

        protected abstract Result doInBackground(Params params);

        protected abstract void onPostExecute(Result result);

        protected void onProgressUpdate(@NotNull Progress value) {
            // Override this method whenever you want update a progress result
        }

        // used for push progress resort to UI
        public void publishProgress(@NotNull Progress value) {
            getHandler().post(() -> onProgressUpdate(value));
        }

        public void execute() {
            execute(null);
        }

        public void execute(Params params) {
            getHandler().post(() -> {
                onPreExecute();
                executor.execute(() -> {
                    Result result = doInBackground(params);
                    getHandler().post(() -> onPostExecute(result));
                });
            });
        }

        public void shutDown() {
            if (executor != null) {
                executor.shutdownNow();
            }
        }

        public boolean isCancelled() {
            return executor == null || executor.isTerminated() || executor.isShutdown();
        }
    }
}