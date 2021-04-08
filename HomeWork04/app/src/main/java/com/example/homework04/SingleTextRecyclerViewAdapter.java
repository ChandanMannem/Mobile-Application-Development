package com.example.homework04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
/*
    Assignment  : homework04
    File Name   : SingleTextRecyclerViewAdapter.java
    Name        : Chandan Mannem, Mahalavanya Sriram
 */

public class SingleTextRecyclerViewAdapter extends RecyclerView.Adapter<SingleTextRecyclerViewAdapter.ViewHolderSingle>{

    DataServices.App app;
    public SingleTextRecyclerViewAdapter(DataServices.App data){
        this.app = data;
    }
    @NonNull
    @Override
    public ViewHolderSingle onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_single_text_recycler_view, parent, false);
        SingleTextRecyclerViewAdapter.ViewHolderSingle viewHolder = new SingleTextRecyclerViewAdapter.ViewHolderSingle(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSingle holder, int position) {
        holder.textViewText.setText(app.genres.get(position));
    }

    @Override
    public int getItemCount() {
        return app.genres.size();
    }

    public static class ViewHolderSingle extends RecyclerView.ViewHolder {

        TextView textViewText;
        public ViewHolderSingle(@NonNull View itemView) {
            super(itemView);
            textViewText = itemView.findViewById(R.id.textViewSingle);
        }
    }
}
