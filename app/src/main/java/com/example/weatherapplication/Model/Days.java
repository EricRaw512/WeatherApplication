package com.example.weatherapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Days implements Parcelable {

    private String datetime;
    private double temp;

    private double tempmax;

    private double tempmin;
    private double humidity;
    private double precipprob;
    private List<String> preciptype;
    private double windSpeed;
    private String conditions;
    private String icon;
    private List<Hours> hours;

    public Days(String datetime, double temp, double tempMax, double tempMin, double humidity, double precipProb, List<String> precipType, double windSpeed, String conditions, String icon, List<Hours> hours) {
        this.datetime = datetime;
        this.temp = temp;
        this.tempmax = tempMax;
        this.tempmin = tempMin;
        this.humidity = humidity;
        this.precipprob = precipProb;
        this.preciptype = precipType;
        this.windSpeed = windSpeed;
        this.conditions = conditions;
        this.icon = icon;
        this.hours = hours;
    }

    protected Days(Parcel in) {
        datetime = in.readString();
        temp = in.readDouble();
        tempmax = in.readDouble();
        tempmin = in.readDouble();
        humidity = in.readDouble();
        precipprob = in.readDouble();
        preciptype = in.createStringArrayList();
        windSpeed = in.readDouble();
        conditions = in.readString();
        icon = in.readString();
        hours = in.createTypedArrayList(Hours.CREATOR);
    }

    public static final Creator<Days> CREATOR = new Creator<Days>() {
        @Override
        public Days createFromParcel(Parcel in) {
            return new Days(in);
        }

        @Override
        public Days[] newArray(int size) {
            return new Days[size];
        }
    };

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTempMax() {
        return tempmax;
    }

    public void setTempMax(double tempMax) {
        this.tempmax = tempMax;
    }

    public double getTempMin() {
        return tempmin;
    }

    public void setTempMin(double tempMin) {
        this.tempmin = tempMin;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPrecipProb() {
        return precipprob;
    }

    public void setPrecipProb(double precipProb) {
        this.precipprob = precipProb;
    }

    public List<String> getPrecipType() {
        return preciptype;
    }

    public void setPrecipType(List<String> precipType) {
        this.preciptype = precipType;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Hours> getHours() {
        return hours;
    }

    public void setHours(List<Hours> hours) {
        this.hours = hours;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(datetime);
        dest.writeDouble(temp);
        dest.writeDouble(tempmax);
        dest.writeDouble(tempmin);
        dest.writeDouble(humidity);
        dest.writeDouble(precipprob);
        dest.writeStringList(preciptype);
        dest.writeDouble(windSpeed);
        dest.writeString(conditions);
        dest.writeString(icon);
        dest.writeTypedList(hours);
    }
}
