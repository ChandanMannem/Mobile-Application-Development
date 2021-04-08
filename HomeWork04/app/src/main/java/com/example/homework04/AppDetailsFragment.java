package com.example.homework04;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*
    Assignment  : ho
    File Name   : AppDetailsFragment.java
    Name        : Chandan Mannem, Mahalavanya Sriram
 */

public class AppDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TOKEN = "token";

    // TODO: Rename and change types of parameters
    private DataServices.App app;

    public AppDetailsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance(DataServices.App app) {
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

        getActivity().setTitle(R.string.app_details);
        TextView textViewName = view.findViewById(R.id.textViewAppDetailsAppName);
        TextView textViewArtist = view.findViewById(R.id.textViewAppDetailsArtist);
        TextView textViewReleaseDate = view.findViewById(R.id.textViewAppDetailsReleaseDate);


        textViewName.setText(app.name);
        textViewArtist.setText(app.artistName);
        textViewReleaseDate.setText(app.releaseDate);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewAppDetails);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        SingleTextRecyclerViewAdapter adapter = new SingleTextRecyclerViewAdapter(app);
        recyclerView.setAdapter(adapter);

        return view;
    }
}