package com.example.midterm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DataServices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
    Assignment  : Midterm
    File Name   : FormsListDetailRecyclerView.java
    Name        : Chandan Mannem
 */
public class FormsListDetailRecyclerView extends RecyclerView.Adapter<FormsListDetailRecyclerView.ViewHolder>{

    DataServices.AuthResponse loginResponse;
    DataServices.Forum forum;
    ArrayList<DataServices.Comment> comments;
    public static InteractWithRecyclerView interact;

    FormsListDetailRecyclerView(DataServices.AuthResponse loginResponse,
            DataServices.Forum forum,
            ArrayList<DataServices.Comment> comments, ForumDetailFragment context){

        this.loginResponse = loginResponse;
        this.forum = forum;
        this.comments = comments;
        this.interact = (InteractWithRecyclerView) context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_form_detail_templet, parent, false);
        FormsListDetailRecyclerView.ViewHolder viewHolder = new FormsListDetailRecyclerView.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataServices.Comment comment = comments.get(position);
//        comment.getCreatedBy().getUid()
        if(loginResponse.getAccount().getUid() == comment.getCreatedBy().getUid()){
            holder.imageButtonFormDetailDelete.setVisibility(ImageButton.VISIBLE);
        }else{
            holder.imageButtonFormDetailDelete.setVisibility(ImageButton.INVISIBLE);
        }
        holder.textViewAuthor.setText(comment.getCreatedBy().getName());
        holder.textViewComment.setText(comment.getText());
        Date date = comment.getCreatedAt();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY hh:mm a");
        holder.textViewCreatedAt.setText(df.format(date));

        holder.imageButtonFormDetailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interact.getCommentDetails(loginResponse, forum.getForumId(), comment.getCommentId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewAuthor;
        TextView textViewComment;
        TextView textViewCreatedAt;
        ImageButton imageButtonFormDetailDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAuthor       = itemView.findViewById(R.id.textViewCommentAuthor);
            textViewComment      = itemView.findViewById(R.id.textViewCommentDesc);
            textViewCreatedAt    = itemView.findViewById(R.id.textViewForumCommentDate);
            imageButtonFormDetailDelete = itemView.findViewById(R.id.imageButtonFormDetailDelete);
        }
    }

    public interface InteractWithRecyclerView{
        public void getCommentDetails(DataServices.AuthResponse loginResponse, long forumId, long commentId);
    }

}
