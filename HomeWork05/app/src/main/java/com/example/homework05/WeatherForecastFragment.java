package com.example.homework05;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherForecastFragment extends Fragment {

    private static final String ARG_CITY = "ARG_CITY";
    private static final String ARG_COUNTRY = "ARG_COUNTRY";
    private static final String ARG_RESPONSE = "ARG_RESPONSE";


    private String city;
    private String country;
    private ForecastResponse response;


    // TODO: Rename and change types and number of parameters
    public static WeatherForecastFragment newInstance(String city, String country, ForecastResponse forecastResponse) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY, city);
        args.putString(ARG_COUNTRY, country);
        args.putSerializable(ARG_RESPONSE, forecastResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city        = getArguments().getString(ARG_CITY);
            country     = getArguments().getString(ARG_COUNTRY);
            response    = (ForecastResponse) getArguments().getSerializable(ARG_RESPONSE);
        }
    }

    RecyclerView recyclerView;
    TextView textViewForecastTitle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);
        recyclerView = view.findViewById(R.id.recylerViewForecast);
        textViewForecastTitle = view.findViewById(R.id.textViewForecastTitle);

        textViewForecastTitle.setText(city + ", " + country);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        WeatherForecastRecyclerView adapater = new WeatherForecastRecyclerView(response);
        recyclerView.setAdapter(adapater);
        return view;
    }


}