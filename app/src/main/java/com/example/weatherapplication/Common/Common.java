package com.example.weatherapplication.Common;

import com.example.weatherapplication.BuildConfig;
import com.example.weatherapplication.Model.Days;
import com.example.weatherapplication.Model.Hours;
import com.example.weatherapplication.Model.Weather;
import com.example.weatherapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Common {
    public static String API_KEY = BuildConfig.API_KEY; //insert your own API Key
    public static String API_LINK = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    public static String apiRequest(String lat, String lon) {
        return String.format("%s,%s?unitGroup=metric&key=%s", lat, lon, API_KEY);
    }

    //change time from "01:00:00" to 1 AM or "13:00:00" to 1 PM
    public static String convertTimeTo12HourFormat(String inputTime) {
        String formattedTime;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("h a");
            LocalTime time = LocalTime.parse(inputTime, inputFormatter);
            formattedTime = time.format(outputFormatter);
        }else {
            // Use ThreeTenABP for devices below API level 26
            org.threeten.bp.format.DateTimeFormatter inputFormatter = org.threeten.bp.format.DateTimeFormatter.ofPattern("HH:mm:ss");
            org.threeten.bp.format.DateTimeFormatter outputFormatter = org.threeten.bp.format.DateTimeFormatter.ofPattern("h a");
            org.threeten.bp.LocalTime time = org.threeten.bp.LocalTime.parse(inputTime, inputFormatter);
            formattedTime = time.format(outputFormatter);
        }

        return formattedTime;
    }

    public static String convertDateFormat(String inputDate) {
        String formattedDate;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEE, dd MMMM");
            LocalDate time = LocalDate.parse(inputDate, inputFormatter);
            formattedDate = time.format(outputFormatter);
        }else {
            // Use ThreeTenABP for devices below API level 26
            org.threeten.bp.format.DateTimeFormatter inputFormatter = org.threeten.bp.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
            org.threeten.bp.format.DateTimeFormatter outputFormatter = org.threeten.bp.format.DateTimeFormatter.ofPattern("EEE, dd MM");
            org.threeten.bp.LocalDate date = org.threeten.bp.LocalDate.parse(inputDate, inputFormatter);
            formattedDate = date.format(outputFormatter);
        }

        return formattedDate;
    }

    public static String convertDateToDay(String inputDate) {
        String formattedDate;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEE");
            LocalDate time = LocalDate.parse(inputDate, inputFormatter);
            formattedDate = time.format(outputFormatter);
        }else {
            // Use ThreeTenABP for devices below API level 26
            org.threeten.bp.format.DateTimeFormatter inputFormatter = org.threeten.bp.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
            org.threeten.bp.format.DateTimeFormatter outputFormatter = org.threeten.bp.format.DateTimeFormatter.ofPattern("EEE");
            org.threeten.bp.LocalDate date = org.threeten.bp.LocalDate.parse(inputDate, inputFormatter);
            formattedDate = date.format(outputFormatter);
        }

        return formattedDate;
    }

    public static int getWeatherIcon(String icon) {
        switch (icon) {
            case "clear_day":
                return R.drawable.clear_day;
            case "clear_night":
                return R.drawable.clear_night;
            case "cloudy":
                return R.drawable.cloudy;
            case "fog":
                return R.drawable.fog;
            case "hail":
                return R.drawable.hail;
            case "partly_cloudy_day":
                return R.drawable.partly_cloudy_day;
            case "partly_cloudy_night":
                return R.drawable.partly_cloudy_night;
            case "rain":
                return R.drawable.rain;
            case "rain_snow":
                return R.drawable.rain_snow;
            case "rain_snow_showers_day":
                return R.drawable.rain_snow_showers_day;
            case "rain_snow_showers_night":
                return R.drawable.rain_snow_showers_night;
            case "showers_day":
                return R.drawable.showers_day;
            case "showers_night":
                return R.drawable.showers_night;
            case "sleet":
                return R.drawable.sleet;
            case "snow":
                return R.drawable.snow;
            case "snow_showers_day":
                return R.drawable.snow_showers_day;
            case "snow_showers_night":
                return R.drawable.snow_showers_night;
            case "thunder":
                return R.drawable.thunder;
            case "thunder_rain":
                return R.drawable.thunder_rain;
            case "thunder_showers_day":
                return R.drawable.thunder_showers_day;
            case "thunder_showers_night":
                return R.drawable.thunder_showers_night;
            case "umbrella":
                return R.drawable.umbrella;
            case "wind":
                return R.drawable.wind;
            case "humidity":
                return R.drawable.humidity;
            case "back":
                return R.drawable.back;
        }

        return R.drawable.clear_day;
    }

    public static ArrayList<Hours> listOfHoursForecast(Weather weather) {
        int index = findIndexOfHoursAfterCurrentTime(weather.getDays().get(0).getHours(), weather.getDays().get(0).getDatetime());
        ArrayList<Hours> hours = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Days day = weather.getDays().get(i);
            int startIndex = (i == 0) ? index : 0;
            if (startIndex == -1) continue;

            for (int j = startIndex; j < day.getHours().size(); j++) {
                hours.add(day.getHours().get(j));
                if (hours.size() == 24) return hours;
            }
        }

        return hours;
    }

    private static int findIndexOfHoursAfterCurrentTime(List<Hours> hours, String date) {
        Calendar currentTime = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < hours.size(); i++) {
            String hourTimeString = date + " " + hours.get(i).getDatetime();

            try {
                Date hourTime = sdf.parse(hourTimeString);
                Calendar hourCalendar = Calendar.getInstance();
                hourCalendar.setTime(hourTime);
                if (hourCalendar.after(currentTime)) {
                    return i;
                }

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        return -1;
    }
}
