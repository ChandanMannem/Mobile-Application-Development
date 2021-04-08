package com.example.homework05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;


public class CitiesFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    CitiesFragmentListener citiesFragmentListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cities, container, false);
        String[] cities = new String[Data.cities.size()];
        int index = 0;
        for(Data.City city : Data.cities){
            cities[index] = city.getCity() + ", " + city.getCountry();
            index++;
        }

        ListView listView = view.findViewById(R.id.ListViewCities);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, cities);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                citiesFragmentListener.onItemClick(position);
            }
        });


        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof  CitiesFragmentListener) {
            citiesFragmentListener = (CitiesFragmentListener) context;
        } else{
            throw new RuntimeException(context.toString() + " must implement LoginListener");
        }
    }

    public interface CitiesFragmentListener{
        public void onItemClick(int position);
    }
}