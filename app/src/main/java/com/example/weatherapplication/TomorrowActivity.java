package com.example.weatherapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.Adapters.TomorrowAdapter;
import com.example.weatherapplication.Common.Common;
import com.example.weatherapplication.Model.Days;

import java.util.ArrayList;

public class TomorrowActivity extends AppCompatActivity {
    private TextView txtTomCond, txtTomTemp, txtTomPreProb, txtTomPreType, txtTomWindSpeed, txtTomHumid;
    private ImageView tomorrowIcon, tomorrowPrecipIcon, tomorrowWindIcon, tomorrowHumidIcon, backIcon;
    private ArrayList<Days> days;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tommorow);

        txtTomCond = (TextView) findViewById(R.id.textTomorrowCondition);
        txtTomTemp = (TextView) findViewById(R.id.textTomorrowTemp);
        txtTomPreProb = (TextView) findViewById(R.id.textTomorrowPrecipProb);
        txtTomPreType = (TextView) findViewById(R.id.textTomorrowPrecipType);
        txtTomWindSpeed = (TextView) findViewById(R.id.textTomorrowWindSpeed);
        txtTomHumid = (TextView) findViewById(R.id.textTomorrowHumidityPercen);
        tomorrowIcon = (ImageView) findViewById(R.id.tomorrowIcon);
        tomorrowPrecipIcon = (ImageView) findViewById(R.id.tomorrowPrecipIcon);
        tomorrowWindIcon = (ImageView) findViewById(R.id.tomorrowWindIcon);
        tomorrowHumidIcon = (ImageView) findViewById(R.id.TomorrowHumidityIcon);
        backIcon = (ImageView) findViewById(R.id.backIcon);

        days = getIntent().getParcelableArrayListExtra("daysList");
        initView();
    }

    private void initView() {
        ArrayList<Days> sevenDays = new ArrayList<>();
        setButton();
        for (int i = 2; i < 8; i++) {
            sevenDays.add(days.get(i));
        }

        Days tomorrowWeather = days.get(1);
        txtTomCond.setText(tomorrowWeather.getConditions());
        txtTomTemp.setText(String.format("%s°C", tomorrowWeather.getTemp()));
        txtTomPreProb.setText(String.format("%s%%", tomorrowWeather.getPrecipProb()));
        txtTomPreType.setText(tomorrowWeather.getPrecipType().get(0));
        txtTomWindSpeed.setText(String.format("%s Km/h", tomorrowWeather.getWindSpeed()));
        txtTomHumid.setText(String.format("%s%%", tomorrowWeather.getHumidity()));
        tomorrowIcon.setImageResource(Common.getWeatherIcon(tomorrowWeather.getIcon()));
        tomorrowPrecipIcon.setImageResource(Common.getWeatherIcon("umbrella"));
        tomorrowWindIcon.setImageResource(Common.getWeatherIcon("wind"));
        tomorrowHumidIcon.setImageResource(Common.getWeatherIcon("humidity"));
        backIcon.setImageResource(Common.getWeatherIcon("back"));

        RecyclerView recyclerView = findViewById(R.id.view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RecyclerView.Adapter adapterTomorrow = new TomorrowAdapter(sevenDays);
        recyclerView.setAdapter(adapterTomorrow);
    }

    private void setButton() {
        ConstraintLayout backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(TomorrowActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
