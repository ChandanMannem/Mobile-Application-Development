package com.example.homework05;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class CurrentWeatherFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_POSITION = "ARG_POSITION";

    // TODO: Rename and change types of parameters
    private int position;
    OkHttpClient client = new OkHttpClient();

    public static CurrentWeatherFragment newInstance(int position) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    TextView textViewTitle;
    TextView textViewTempValue;
    TextView textViewTempMaxValue;
    TextView textViewTempMinValue;
    TextView textViewDescValue;
    TextView textViewHumidityValue;
    TextView textViewWindSpeedValue;
    TextView textViewWindDegreeValue;
    TextView textViewCloudinessValue;
    Button buttonForecast;

    WeatherFragmentListener weatherFragmentListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        textViewTitle           = view.findViewById(R.id.textViewTitle);
        textViewTempValue       = view.findViewById(R.id.textViewTempValue);
        textViewTempMaxValue    = view.findViewById(R.id.textViewTempMaxValue);
        textViewTempMinValue    = view.findViewById(R.id.textViewTempMinValue);
        textViewDescValue       = view.findViewById(R.id.textViewDescValue);
        textViewHumidityValue   = view.findViewById(R.id.textViewHumidityValue);
        textViewWindSpeedValue  = view.findViewById(R.id.textViewWindSpeedValue);
        textViewWindDegreeValue = view.findViewById(R.id.textViewWindDegreeValue);
        textViewCloudinessValue = view.findViewById(R.id.textViewCloudinessValue);
        buttonForecast          = view.findViewById(R.id.buttonForeCast);

        buttonForecast.setEnabled(false);

        String city     = Data.cities.get(position).getCity();
        String country  = Data.cities.get(position).getCountry();

        buttonForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonForecast.setEnabled(false);
                calForecastAPI(city, country);
            }
        });


        calCurrentAPI(city, country);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof WeatherFragmentListener) {
            weatherFragmentListener = (WeatherFragmentListener) context;
        } else{
            throw new RuntimeException(context.toString() + " must implement LoginListener");
        }
    }

    private void calCurrentAPI(String city , String country) {

        final String API_KEY = "5de5ffb972e1a10fd449ce78e18200c7";

//        api.openweathermap.org/data/2.5/weather?q=Charlotte&appid=5de5ffb972e1a10fd449ce78e18200c7

        HttpUrl.Builder builder = new HttpUrl.Builder();
        HttpUrl url = builder.scheme("https")
                .host("api.openweathermap.org")
                .addPathSegment("data")
                .addPathSegment("2.5")
                .addPathSegment("weather")
                .addQueryParameter("q", city)
                .addQueryParameter("appid", API_KEY).build();

        Request request = new Request.Builder()
                .url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(getActivity(), "API Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){

                    Gson gson = new Gson();
                    JsonResponse jsonResponse = gson.fromJson(response.body().charStream(), JsonResponse.class);
                    Log.d("TAG", "onResponse: "+ jsonResponse.toString());

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewTitle.setText(city + "," + country);
                            textViewTempValue.setText(jsonResponse.main.getTemp());
                            textViewTempMaxValue.setText(jsonResponse.main.getTemp_max());
                            textViewTempMinValue.setText(jsonResponse.main.getTemp_min());
                            textViewDescValue.setText(jsonResponse.weather.get(0).description);
                            textViewHumidityValue.setText(jsonResponse.main.getHumidity());
                            textViewWindSpeedValue.setText(jsonResponse.wind.getSpeed());
                            textViewWindDegreeValue.setText(jsonResponse.wind.getDeg());
                            textViewCloudinessValue.setText(jsonResponse.clouds.getAll());
                            buttonForecast.setEnabled(true);
                        }
                    });

                }else{
                    Toast.makeText(getActivity(), "API Error", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "onResponse: " + request.body().toString());
                }
            }
        });

    }

    public void calForecastAPI(String city, String country) {

        final String API_KEY = "5de5ffb972e1a10fd449ce78e18200c7";


        HttpUrl.Builder builder = new HttpUrl.Builder();
        HttpUrl url = builder.scheme("https")
                .host("api.openweathermap.org")
                .addPathSegment("data")
                .addPathSegment("2.5")
                .addPathSegment("forecast")
                .addQueryParameter("q", city)
                .addQueryParameter("appid", API_KEY).build();

        Request request = new Request.Builder()
                .url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText( getActivity(), "API Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){

                    Gson gson = new Gson();
                    ForecastResponse forecastResponse = gson.fromJson(response.body().charStream(), ForecastResponse.class);
                    Log.d("TAG", "onResponse: "+ forecastResponse.toString());

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            buttonForecast.setEnabled(true);

                            weatherFragmentListener.onButtonClick(city, country, forecastResponse);
                        }
                    });

                }else{
                    Toast.makeText(getActivity(), "API Error", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "onResponse: " + request.body().toString());
                }
            }
        });

    }

    public interface WeatherFragmentListener{
        public void onButtonClick(String city, String country, ForecastResponse forecastResponse);
    }

}