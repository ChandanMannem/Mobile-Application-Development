package com.example.homework3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
/*
    Assignment  : homework3
    File Name   : AppListFragment.java
    Name        : Chandan Mannem, Mahalavanya Sriram
 */


public class AppListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TOKEN = "apps";
    private static final String ARG_CAT = "category";
    AppListFragmentListener AppListFragmentListener;

    // TODO: Rename and change types of parameters

    ArrayList<DataServices.App> apps;
    String category;

    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance(ArrayList<DataServices.App> apps, String category) {
        AppListFragment fragment = new AppListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TOKEN, apps);
        args.putString(ARG_CAT, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            apps = (ArrayList<DataServices.App>) getArguments().getSerializable(ARG_TOKEN);
            category = (String) getArguments().getSerializable(ARG_CAT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_app_list, container, false);
        getActivity().setTitle(category);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(apps, AppListFragmentListener);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof AppCategoriesFragment.AppCategoriesFragmentListener){
            AppListFragmentListener = (AppListFragmentListener) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement LoginListener");
        }

    }

    public interface  AppListFragmentListener{
        void setAppData(DataServices.App app);
    }
}