package com.example.inclass05;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_APP = "app";

    // TODO: Rename and change types of parameters
    private DataServices.App app;

    public AppDetailsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AppDetailsFragment newInstance(DataServices.App app) {
        AppDetailsFragment fragment = new AppDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_APP, app);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            app = (DataServices.App) getArguments().getSerializable(ARG_APP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_details, container, false);
        TextView textViewName = view.findViewById(R.id.textViewAppDetailsAppName);
        TextView textViewArtist = view.findViewById(R.id.textViewAppDetailsArtist);
        TextView textViewReleaseDate = view.findViewById(R.id.textViewAppDetailsReleaseDate);
        ListView listViewAppDetails = view.findViewById(R.id.listViewAppDetails);


        textViewName.setText(app.name);
        textViewArtist.setText(app.artistName);
        textViewReleaseDate.setText(app.releaseDate);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, app.genres);
        listViewAppDetails.setAdapter(adapter);
        return view;
    }
}