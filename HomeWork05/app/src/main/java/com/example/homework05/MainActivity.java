package com.example.homework05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CitiesFragment.CitiesFragmentListener,
        CurrentWeatherFragment.WeatherFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.cities);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerId, new CitiesFragment())
                .commit();
    }

    @Override
    public void onItemClick(int position) {
        setTitle(R.string.cureent_weather);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerId, CurrentWeatherFragment.newInstance(position))
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onButtonClick(String city, String country, ForecastResponse forecastResponse) {
        setTitle(R.string.weather_forecast);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerId, WeatherForecastFragment.newInstance(city, country, forecastResponse))
                .addToBackStack(null)
                .commit();
    }


}