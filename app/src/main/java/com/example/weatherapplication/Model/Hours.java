package com.example.weatherapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Hours implements Parcelable {

    private String datetime;
    private double temp;
    private String icon;

    public Hours(String datetime, double temp, String icon) {
        this.datetime = datetime;
        this.temp = temp;
        this.icon = icon;
    }

    protected Hours(Parcel in) {
        datetime = in.readString();
        temp = in.readDouble();
        icon = in.readString();
    }

    public static final Creator<Hours> CREATOR = new Creator<Hours>() {
        @Override
        public Hours createFromParcel(Parcel in) {
            return new Hours(in);
        }

        @Override
        public Hours[] newArray(int size) {
            return new Hours[size];
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(datetime);
        dest.writeDouble(temp);
        dest.writeString(icon);
    }
}
