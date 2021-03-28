package com.example.midterm;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DataServices;

import org.w3c.dom.Text;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/*
    Assignment  : Midterm
    File Name   : FormsListRecyclerView.java
    Name        : Chandan Mannem
 */

public class FormsListRecyclerView extends RecyclerView.Adapter<FormsListRecyclerView.ViewHolder> {

    private static final String TAG = "TAG";
    ArrayList<DataServices.Forum> forums;
    DataServices.AuthResponse loginResponse;

    public static InteractWithRecyclerView interact;

    FormsListRecyclerView(ArrayList<DataServices.Forum> forums,
                          FormsListFragment context,
                          DataServices.AuthResponse loginResponse ){
        this.forums = forums;
        this.interact = (InteractWithRecyclerView) context;
        this.loginResponse = loginResponse;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_forms_templet, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataServices.Forum forum = forums.get(position);

        Log.d(TAG, "onBindViewHolder: " + loginResponse.getAccount().getUid() + " -> " + forum.getCreatedBy().getUid());
        if(loginResponse.getAccount().getUid() == forum.getCreatedBy().getUid()){
            holder.imageButton.setVisibility(ImageButton.VISIBLE);
        }else{
            holder.imageButton.setVisibility(ImageButton.INVISIBLE);
        }

        if(forum.getLikedBy().contains(loginResponse.getAccount())){
           holder.ImageButtonDisFav.setVisibility(View.VISIBLE);
            holder.ImageButtonFav.setVisibility(View.INVISIBLE);
        }else{
            holder.ImageButtonDisFav.setVisibility(View.INVISIBLE);
            holder.ImageButtonFav.setVisibility(View.VISIBLE);
        }

        holder.textViewHeading.setText(forum.getTitle());
        holder.textViewSubHeading.setText(forum.getCreatedBy().getName());
        holder.textViewDesc.setText(forum.getDescription());
        holder.textViewComments.setText(forum.getLikedBy().size() + " Likes");
        Date date = forum.getCreatedAt();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY hh:mm a");
        holder.textViewTimeStamp.setText(df.format(date));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call the list details
                interact.onItemClicked(loginResponse, forum);
            }
        });

        holder.ImageButtonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dislike it
                holder.ImageButtonFav.setVisibility(View.INVISIBLE);
                holder.ImageButtonDisFav.setVisibility(View.VISIBLE);
                interact.onLike(true, forum.getForumId());
            }
        });

        holder.ImageButtonDisFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ImageButtonFav.setVisibility(View.VISIBLE);
                holder.ImageButtonDisFav.setVisibility(View.INVISIBLE);
                interact.onLike(false, forum.getForumId());
            }
        });

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interact.getForumDetails(loginResponse, forum.getForumId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return forums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewHeading;
        TextView textViewSubHeading;
        TextView textViewDesc;
        TextView textViewComments;
        TextView textViewTimeStamp;
        ImageButton imageButton;
        ImageButton ImageButtonFav;
        ImageButton ImageButtonDisFav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHeading         = itemView.findViewById(R.id.textViewForumsHeading);
            textViewSubHeading      = itemView.findViewById(R.id.textViewFormsSubHeading);
            textViewDesc            = itemView.findViewById(R.id.textViewForumsDesc);
            textViewComments        = itemView.findViewById(R.id.textViewForumsLikes);
            textViewTimeStamp       = itemView.findViewById(R.id.textViewForumsDate);
            imageButton             = itemView.findViewById(R.id.imageButton);
            ImageButtonFav          = itemView.findViewById(R.id.ImageButtonFav);
            ImageButtonDisFav       = itemView.findViewById(R.id.imageButtonDisFav);
        }
    }

    public interface InteractWithRecyclerView{
        public void onItemClicked(DataServices.AuthResponse loginResponse, DataServices.Forum forum);
        public void onLike(boolean isLiked, long formId);
        public void getForumDetails(DataServices.AuthResponse loginResponse, long forumId);
    }

}
