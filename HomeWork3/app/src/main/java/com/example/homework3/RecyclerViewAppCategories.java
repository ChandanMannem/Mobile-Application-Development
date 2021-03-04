package com.example.homework3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/*
    Assignment  : homework3
    File Name   : RecyclerViewAppCategories.java
    Name        : Chandan Mannem, Mahalavanya Sriram
 */

public class RecyclerViewAppCategories extends RecyclerView.Adapter<RecyclerViewAppCategories.ViewHolderAppCategories>{

    ArrayList<String> apps;
    AppCategoriesFragment.AppCategoriesFragmentListener appCategoriesFragmentListener;
    String token;
    public RecyclerViewAppCategories(ArrayList<String> data,
                                     String token,
                                     AppCategoriesFragment.AppCategoriesFragmentListener listener){
        this.apps = data;
        this.appCategoriesFragmentListener = listener;
        this.token = token;
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String category = apps.get(position);
                DataServices.getAppsByCategory(token, category, new DataServices.DataResponse<DataServices.App>() {
                    @Override
                    public void onSuccess(ArrayList<DataServices.App> data) {
                        appCategoriesFragmentListener.setCategoryToken(data, category);
                    }

                    @Override
                    public void onFailure(DataServices.RequestException exception) {
//                        Toast.makeText(, exception.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
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
}
