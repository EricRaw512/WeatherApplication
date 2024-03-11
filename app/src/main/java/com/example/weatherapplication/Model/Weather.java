package com.example.weatherapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Weather implements Parcelable {

    private String address;
    private List<Days> days;

    public Weather() {
    }

    protected Weather(Parcel in) {
        address = in.readString();
        days = in.createTypedArrayList(Days.CREATOR);
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    public List<Days> getDays() {
        return days;
    }

    public void setDays(List<Days> days) {
        this.days = days;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeTypedList(days);
    }
}
