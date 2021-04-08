package com.example.homework05;

import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherForecastRecyclerView extends RecyclerView.Adapter<WeatherForecastRecyclerView.ViewHolder>{

    ForecastResponse forecastResponse;

    WeatherForecastRecyclerView(ForecastResponse forecastResponse){
        this.forecastResponse = forecastResponse;
    }


    @NonNull
    @Override
    public WeatherForecastRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_weather_forcast, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherForecastRecyclerView.ViewHolder holder, int position) {

        JsonResponse itemResponse = forecastResponse.list.get(position);
        holder.textViewTime.setText(itemResponse.dt_txt);
        holder.textViewForecastTemp.setText(itemResponse.main.getTemp());
        holder.textViewForecastTempMax.setText(itemResponse.main.getTemp_max());
        holder.textViewForecastTempMin.setText(itemResponse.main.getTemp_min());
        holder.textViewForecastHumidity.setText(itemResponse.main.getHumidity());
        holder.textViewForecastClouds.setText(itemResponse.clouds.getAll());

    }

    @Override
    public int getItemCount() {
        return forecastResponse.list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTime;
        TextView textViewForecastTemp;
        TextView textViewForecastTempMax;
        TextView textViewForecastTempMin;
        TextView textViewForecastHumidity;
        TextView textViewForecastClouds;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewForecastTemp = itemView.findViewById(R.id.textViewForecastTemp);
            textViewForecastTempMax = itemView.findViewById(R.id.textViewForecastTempMax);
            textViewForecastTempMin = itemView.findViewById(R.id.textViewForecastTempMin);
            textViewForecastHumidity = itemView.findViewById(R.id.textViewForecastHumidity);
            textViewForecastClouds = itemView.findViewById(R.id.textViewForecastClouds);
        }
    }


}
