package com.example.homework05;

import java.io.Serializable;
import java.util.ArrayList;

public class JsonResponse implements Serializable {
    String name;
    ArrayList<Weather> weather;
    Main main;
    Wind wind;
    Clouds clouds;
    String dt_txt;

    @Override
    public String toString() {
        return "JsonResponse{" +
                "name='" + name + '\'' +
                ", weather=" + weather +
                ", main=" + main +
                ", wind=" + wind +
                ", clouds=" + clouds +
                ", dt_txt='" + dt_txt + '\'' +
                '}';
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

}
