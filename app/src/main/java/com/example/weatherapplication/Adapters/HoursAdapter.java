package com.example.weatherapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.Common.Common;
import com.example.weatherapplication.Model.Hours;
import com.example.weatherapplication.R;

import java.util.ArrayList;

public class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.ViewHolder> {

    ArrayList<Hours> items;

    public HoursAdapter(ArrayList<Hours> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public HoursAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_hours, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull HoursAdapter.ViewHolder holder, int position) {
        Hours hours = items.get(position);
        holder.datetimeTxt.setText(Common.convertTimeTo12HourFormat(hours.getDatetime()));
        holder.tempTxt.setText(String.format("%s°", hours.getTemp()));
        holder.icon.setImageResource(Common.getWeatherIcon(hours.getIcon()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView datetimeTxt, tempTxt;
        ImageView icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            datetimeTxt = itemView.findViewById(R.id.datetimeTxt);
            tempTxt = itemView.findViewById(R.id.tempTxt);
            icon = itemView.findViewById(R.id.icon);
        }
    }
}
