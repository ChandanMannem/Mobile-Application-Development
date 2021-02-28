package com.example.inclass05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopPaidFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopPaidFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TOKEN = "apps";
    TopPaidFragmentListener topPaidFragmentListener;

    // TODO: Rename and change types of parameters
//    private String token;
    ArrayList<DataServices.App> apps;

    // TODO: Rename and change types and number of parameters
    public static TopPaidFragment newInstance(ArrayList<DataServices.App> apps) {
        TopPaidFragment fragment = new TopPaidFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TOKEN, apps);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            apps = (ArrayList<DataServices.App>) getArguments().getSerializable(ARG_TOKEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_top_paid, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(apps, topPaidFragmentListener);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof AppCategoriesFragment.AppCategoriesFragmentListener){
            topPaidFragmentListener = (TopPaidFragmentListener) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement LoginListener");
        }

    }

    public interface  TopPaidFragmentListener{
        void setAppData(DataServices.App app);
    }
}