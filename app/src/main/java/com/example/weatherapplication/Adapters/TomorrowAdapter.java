package com.example.weatherapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.Common.Common;
import com.example.weatherapplication.Model.Days;
import com.example.weatherapplication.R;

import java.util.ArrayList;

public class TomorrowAdapter extends RecyclerView.Adapter<TomorrowAdapter.ViewHolder> {

    ArrayList<Days> items;

    public TomorrowAdapter(ArrayList<Days> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public TomorrowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_tommorow, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull TomorrowAdapter.ViewHolder holder, int position) {
        Days days = items.get(position);
        holder.txtDay.setText(Common.convertDateToDay(days.getDatetime()));
        holder.txtStatus.setText(days.getConditions());
        holder.txtLow.setText(String.format("%s°", days.getTempMin()));
        holder.txtHigh.setText(String.format("%s°", days.getTempMax()));
        holder.icon.setImageResource(Common.getWeatherIcon(days.getIcon()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDay, txtStatus, txtLow, txtHigh;
        ImageView icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDay = itemView.findViewById(R.id.textDay);
            txtStatus = itemView.findViewById(R.id.textStatus);
            txtLow = itemView.findViewById(R.id.textLow);
            txtHigh = itemView.findViewById(R.id.textHigh);
            icon = itemView.findViewById(R.id.tomorrowHolderIcon);
        }
    }
}
