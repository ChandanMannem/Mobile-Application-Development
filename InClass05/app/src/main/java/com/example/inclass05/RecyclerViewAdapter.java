package com.example.inclass05;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<DataServices.App> apps;
    TopPaidFragment.TopPaidFragmentListener topPaidFragmentListener;

    public RecyclerViewAdapter(ArrayList<DataServices.App> data, TopPaidFragment.TopPaidFragmentListener topPaidFragmentListener){
        this.apps = data;
        this.topPaidFragmentListener = topPaidFragmentListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycler_view_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataServices.App app = apps.get(position);
        Log.d("TAG", "onBindViewHolder: " + app.name + " " + app.artistName + " " +app.releaseDate);
        holder.textViewAppName.setText(app.name);
        holder.textViewArtist.setText(app.artistName);
        holder.textViewReleaseDate.setText(app.releaseDate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topPaidFragmentListener.setAppData(app);
            }
        });
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewAppName;
        TextView textViewArtist;
        TextView textViewReleaseDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAppName = itemView.findViewById(R.id.textViewCardApp);
            textViewArtist = itemView.findViewById(R.id.textViewCardArtist);
            textViewReleaseDate = itemView.findViewById(R.id.textViewCardReleaseDate);
        }
    }
}
