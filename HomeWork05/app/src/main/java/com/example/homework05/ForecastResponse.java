package com.example.homework05;

import java.io.Serializable;
import java.util.ArrayList;

public class ForecastResponse implements Serializable {

    ArrayList<JsonResponse> list;

    public ArrayList<JsonResponse> getList() {
        return list;
    }

    public void setList(ArrayList<JsonResponse> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ForecastResponse{" +
                "list=" + list +
                '}';
    }
}
