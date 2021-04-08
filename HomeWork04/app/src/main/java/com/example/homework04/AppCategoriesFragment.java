package com.example.homework04;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    Assignment  : homework04
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
    Handler handler;
    Button buttonLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_categories, container, false);

        getActivity().setTitle(R.string.app_categories);
        TextView textViewWelcome = view.findViewById(R.id.textViewWelcome);

        recyclerView = view.findViewById(R.id.recyclerViewAppCategories);
        buttonLogout = view.findViewById(R.id.buttonLogout);


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                switch (msg.what){
                    case doAppCategoryWork.STATUS_START:
                        buttonLogout.setEnabled(false);
                        break;
                    case doAppCategoryWork.STATUS_SUCCESS:
                        apps = (ArrayList<String>) msg.getData().getSerializable(doAppCategoryWork.APP_DATA);
                        DataServices.Account account = (DataServices.Account) msg.getData().getSerializable(doAppCategoryWork.ACCOUNT);
                        textViewWelcome.setText("Welcome " + account.getName());

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        RecyclerViewAppCategories adapter = new RecyclerViewAppCategories(apps, token,  appCategoriesFragmentListener, view);
                        recyclerView.setAdapter(adapter);

                        break;
                    case doAppCategoryWork.STATUS_FAILURE:
                        Toast.makeText(getActivity(), (String)msg.obj, Toast.LENGTH_SHORT).show();
                        break;
                    case doAppCategoryWork.STATUS_STOP:
                        buttonLogout.setEnabled(true);
                        break;
                }
                return false;
            }
        });


        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        threadPool.execute(new doAppCategoryWork(token));


        buttonLogout.setOnClickListener(new View.OnClickListener() {
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

    private class doAppCategoryWork implements Runnable{
        private  String token;
        public static final int STATUS_START   = 0x00;
        public static final int STATUS_SUCCESS = 0x01;
        public static final int STATUS_FAILURE = 0x02;
        public static final int STATUS_STOP    = 0x03;

        public static final String ACCOUNT = "ACCOUNT";
        public static final String APP_DATA = "APP_DATA";

        doAppCategoryWork(String token){
            this.token = token;
        }
        @Override
        public void run() {
            Message message = new Message();
            message.what = STATUS_START;
            handler.sendMessage(message);

            try {
                DataServices.Account account = DataServices.getAccount(token);
                ArrayList<String> data = DataServices.getAppCategories(token);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ACCOUNT, account);
                bundle.putSerializable(APP_DATA, data);
                message = new Message();
                message.what = STATUS_SUCCESS;
                message.setData(bundle);
                handler.sendMessage(message);
            } catch (DataServices.RequestException e) {
                message = new Message();
                message.what = STATUS_FAILURE;
                message.obj = e.getMessage();
                handler.sendMessage(message);
            }

            message = new Message();
            message.what = STATUS_STOP;
            handler.sendMessage(message);

        }
    }
}