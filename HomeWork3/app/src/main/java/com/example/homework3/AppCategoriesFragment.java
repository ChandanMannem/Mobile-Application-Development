package com.example.homework3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/*
    Assignment  : homework3
    File Name   : AppCategoriesFragment.java
    Name        : Chandan Mannem, Mahalavanya Sriram
 */

public class AppCategoriesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TOKEN = "token";

    // TODO: Rename and change types of parameters
    private String token;


    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance(String token) {
        AppCategoriesFragment fragment = new AppCategoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TOKEN, token);
        fragment.setArguments(args);
        return fragment;
    }

    AppCategoriesFragmentListener appCategoriesFragmentListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            token = getArguments().getString(ARG_TOKEN);
        }
    }

    ArrayList<String> apps;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_categories, container, false);

        getActivity().setTitle(R.string.app_categories);
        TextView textViewWelcome = view.findViewById(R.id.textViewWelcome);

        recyclerView = view.findViewById(R.id.recyclerViewAppCategories);




        DataServices.getAccount(token, new DataServices.AccountResponse() {
            @Override
            public void onSuccess(DataServices.Account account) {

                DataServices.getAppCategories(token, new DataServices.DataResponse<String>() {
                    @Override
                    public void onSuccess(ArrayList<String> data) {

                        apps = data;
                        textViewWelcome.setText("Welcome " + account.getName());

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        RecyclerViewAppCategories adapter = new RecyclerViewAppCategories(data, token,  appCategoriesFragmentListener);

                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(DataServices.RequestException exception) {
                        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure(DataServices.RequestException exception) {
                Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        view.findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appCategoriesFragmentListener.Logout();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof AppCategoriesFragment.AppCategoriesFragmentListener){
            appCategoriesFragmentListener = (AppCategoriesFragmentListener) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement LoginListener");
        }
    }

    public interface  AppCategoriesFragmentListener{
        void setCategoryToken(ArrayList<DataServices.App> apps, String category);
        void Logout();
    }

    public void updateValues(String token){
        this.token = token;
    }
}