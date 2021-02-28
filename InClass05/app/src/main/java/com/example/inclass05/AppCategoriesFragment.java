package com.example.inclass05;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppCategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppCategoriesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TOKEN = "token";

    // TODO: Rename and change types of parameters
    private String token;


    // TODO: Rename and change types and number of parameters
    public static AppCategoriesFragment newInstance(String token) {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_categories, container, false);

        TextView textViewWelcome = view.findViewById(R.id.textViewWelcome);
        ListView listViewAppCategories = view.findViewById(R.id.listViewAppCategories);


        DataServices.getAccount(token, new DataServices.AccountResponse() {
            @Override
            public void onSuccess(DataServices.Account account) {

                DataServices.getAppCategories(token, new DataServices.DataResponse<String>() {
                    @Override
                    public void onSuccess(ArrayList<String> data) {

                        apps = data;
                        textViewWelcome.setText("Welcome " + account.getName());
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                                android.R.layout.simple_list_item_1, android.R.id.text1, data);
                        listViewAppCategories.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(DataServices.RequestException exception) {
                        Toast.makeText(getActivity(), exception.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure(DataServices.RequestException exception) {
                Toast.makeText(getActivity(), exception.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        listViewAppCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String category = apps.get(position);
                DataServices.getAppsByCategory(token, category, new DataServices.DataResponse<DataServices.App>() {
                    @Override
                    public void onSuccess(ArrayList<DataServices.App> data) {
                        appCategoriesFragmentListener.setCategoryToken(data);
                    }

                    @Override
                    public void onFailure(DataServices.RequestException exception) {
                        Toast.makeText(getActivity(), exception.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
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
        void setCategoryToken(ArrayList<DataServices.App> apps);
        void Logout();
    }

    public void updateValues(String token){
        this.token = token;
    }
}