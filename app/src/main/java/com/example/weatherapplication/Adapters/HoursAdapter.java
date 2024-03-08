package com.example.weatherapplication.Adapters;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.ViewHolder> {

    ArrayList<Hours> items;
    Context context;

    public HoursAdapter(ArrayList<Hours> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public HoursAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_hours, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull HoursAdapter.ViewHolder holder, int position) {
        holder.datetimeTxt.setText(Common.timeFormat(items.get(position).getDatetime()));
        holder.tempTxt.setText((int) items.get(position).getTemp());
        int drawableResourceId = holder.itemView.getResources()
                .getIdentifier(items.get(position).getIcon(), "drawable", holder.itemView.getContext().getPackageName());


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
