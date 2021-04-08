package com.example.homework04;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    Assignment  : homework04
    File Name   : RecyclerViewAppCategories.java
    Name        : Chandan Mannem, Mahalavanya Sriram
 */

public class RecyclerViewAppCategories extends RecyclerView.Adapter<RecyclerViewAppCategories.ViewHolderAppCategories>{

    ArrayList<String> apps;
    AppCategoriesFragment.AppCategoriesFragmentListener appCategoriesFragmentListener;
    String token;
    Handler handler;
    View view;
    public RecyclerViewAppCategories(ArrayList<String> data,
                                     String token,
                                     AppCategoriesFragment.AppCategoriesFragmentListener listener,
                                     View view){
        this.apps = data;
        this.appCategoriesFragmentListener = listener;
        this.token = token;
        this.view = view;
    }

    @NonNull
    @Override
    public ViewHolderAppCategories onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_single_text_recycler_view, parent, false);
        RecyclerViewAppCategories.ViewHolderAppCategories viewHolder = new RecyclerViewAppCategories.ViewHolderAppCategories(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAppCategories holder, int position) {
        holder.textViewText.setText(apps.get(position));
        Button buttonLogout = view.findViewById(R.id.buttonLogout);


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                switch (msg.what){
                    case doAppCategoriesWork.STATUS_START:
                        buttonLogout.setEnabled(false);
                        break;
                    case doAppCategoriesWork.STATUS_SUCCESS:
                        ArrayList<DataServices.App> data = (ArrayList<DataServices.App>) msg.getData().getSerializable(doAppCategoriesWork.APP_CATEGORY_DATA);
                        String category = (String) msg.getData().getString(doAppCategoriesWork.CATEGORY);
                        appCategoriesFragmentListener.setCategoryToken(data, category);
                        break;
                    case doAppCategoriesWork.STATUS_FAILURE:
                        break;
                    case doAppCategoriesWork.STATUS_STOP:
                        buttonLogout.setEnabled(true);
                        break;
                }
                return false;
            }
        });


        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = apps.get(position);
                Log.d("TAG", "onClick: "+ category);
                threadPool.execute(new doAppCategoriesWork(category));
            }
        });
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public static class ViewHolderAppCategories extends RecyclerView.ViewHolder{

        TextView textViewText;
        public ViewHolderAppCategories(@NonNull View itemView) {
            super(itemView);
            textViewText = itemView.findViewById(R.id.textViewSingle);
        }
    }

    //--------Thread
    private class doAppCategoriesWork implements Runnable{
        private String category;

        public static final int STATUS_START   = 0x00;
        public static final int STATUS_SUCCESS = 0x01;
        public static final int STATUS_FAILURE = 0x02;
        public static final int STATUS_STOP    = 0x03;

        public static final String APP_CATEGORY_DATA = "APP_CATEGORY_DATA";
        public static final String CATEGORY = "CATEGORY";

        doAppCategoriesWork(String category){

            this.category = category;
        }
        @Override
        public void run() {
            Message message = new Message();
            message.what = STATUS_START;
            handler.sendMessage(message);

            try {
                ArrayList<DataServices.App> data = DataServices.getAppsByCategory(token, category);
                Log.d("TAG", "run: "+ data.toString());
                Bundle bundle = new Bundle();
                bundle.putString(CATEGORY, category);
                bundle.putSerializable(APP_CATEGORY_DATA, data);
                message = new Message();
                message.what = STATUS_SUCCESS;
                message.setData(bundle);
                handler.sendMessage(message);
            } catch (DataServices.RequestException e) {
                Log.d("TAG", "run: " + e.getMessage());
            }

            message = new Message();
            message.what = STATUS_STOP;
            handler.sendMessage(message);

        }
    }

}
