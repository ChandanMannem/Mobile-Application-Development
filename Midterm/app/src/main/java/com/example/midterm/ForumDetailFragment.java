package com.example.midterm;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataServices;

import java.util.ArrayList;

/*
    Assignment  : Midterm
    File Name   : ForumDetailFragment.java
    Name        : Chandan Mannem
 */
public class ForumDetailFragment extends Fragment implements FormsListDetailRecyclerView.InteractWithRecyclerView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LOGIN_RESPONSE = "ARG_LOGIN_RESPONSE";
    private static final String ARG_FORM_ID = "ARG_FORM_ID";

    // TODO: Rename and change types of parameters
    private DataServices.AuthResponse loginResponse;
    private DataServices.Forum forum;


    // TODO: Rename and change types and number of parameters
    public static ForumDetailFragment newInstance(DataServices.AuthResponse loginResponse, DataServices.Forum forum) {
        ForumDetailFragment fragment = new ForumDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOGIN_RESPONSE, loginResponse);
        args.putSerializable(ARG_FORM_ID, forum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loginResponse = (DataServices.AuthResponse) getArguments().getSerializable(ARG_LOGIN_RESPONSE);
            forum = (DataServices.Forum) getArguments().getSerializable(ARG_FORM_ID);
        }
    }

    TextView textViewHeading;
    TextView textViewSubHeading;
    TextView textViewDesc;
    TextView textViewComments;
    RecyclerView recyclerView;
    EditText editTextComment;
    Button buttonPost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_forum_detail, container, false);
        textViewHeading         = view.findViewById(R.id.textViewForumDetailHeading);
        textViewSubHeading      = view.findViewById(R.id.textViewForumDetailSubHeading);
        textViewDesc            = view.findViewById(R.id.textViewForumDetailDescription);
        textViewComments        = view.findViewById(R.id.textViewForumDetailLikes);
        editTextComment         = view.findViewById(R.id.editTextPost);
        buttonPost              = view.findViewById(R.id.buttonPost);


        textViewHeading.setText(forum.getTitle());
        textViewSubHeading.setText(forum.getCreatedBy().getName());
        textViewDesc.setText(forum.getDescription());


        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = editTextComment.getText().toString();
                new doAsyncAddCommentWork(loginResponse, forum).execute(comment);
            }
        });
        recyclerView = view.findViewById(R.id.forumsDetailsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        new doAsyncForumDetailsWork(loginResponse, forum).execute();

        return view;
    }

    @Override
    public void getCommentDetails(DataServices.AuthResponse loginResponse, long forumId, long commentId) {
        new doAsyncDeleteCommentWork(loginResponse, forumId, commentId).execute();
    }

    private class doAsyncForumDetailsWork extends AsyncTask<Void, Void, ArrayList<DataServices.Comment>> {

        DataServices.AuthResponse loginResponse;
        DataServices.Forum forum;
        String forums_error;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            buttonPost.setEnabled(false);
            editTextComment.setText("");
            editTextComment.setEnabled(false);
        }

        doAsyncForumDetailsWork(DataServices.AuthResponse loginResponse, DataServices.Forum forum){
            this.loginResponse = loginResponse;
            this.forum = forum;
        }

        @Override
        protected ArrayList<DataServices.Comment> doInBackground(Void... voids) {
            ArrayList<DataServices.Comment> comments = null;

            try {
                comments = DataServices.getForumComments(loginResponse.getToken(), forum.getForumId());
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
                forums_error = e.getMessage();
            }

            return comments;
        }


        @Override
        protected void onPostExecute(ArrayList<DataServices.Comment> comments) {
            super.onPostExecute(comments);
            if (comments == null){
                Toast.makeText(getActivity(), forums_error, Toast.LENGTH_SHORT).show();
            }else{

                textViewComments.setText(comments.size() + " Comments");

                FormsListDetailRecyclerView adapter = new FormsListDetailRecyclerView(loginResponse,
                        forum, comments, ForumDetailFragment.this);

                recyclerView.setAdapter(adapter);
            }

            buttonPost.setEnabled(true);
            editTextComment.setEnabled(true);

        }


    }

    private class doAsyncAddCommentWork extends AsyncTask<String, Void, DataServices.Comment> {

        DataServices.AuthResponse loginResponse;
        DataServices.Forum forum;
        String forums_error;

        doAsyncAddCommentWork(DataServices.AuthResponse loginResponse, DataServices.Forum forum){
            this.loginResponse = loginResponse;
            this.forum = forum;
        }

        @Override
        protected DataServices.Comment doInBackground(String... strings) {

            DataServices.Comment comment = null;
            try {
                comment = DataServices.createComment(loginResponse.getToken(), forum.getForumId(), strings[0]);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
                forums_error = e.getMessage();
            }

            return comment;
        }


        @Override
        protected void onPostExecute(DataServices.Comment comment) {
            super.onPostExecute(comment);
            if (comment == null){
                Toast.makeText(getActivity(), forums_error, Toast.LENGTH_SHORT).show();
                buttonPost.setEnabled(true);
                editTextComment.setEnabled(true);
            }else{
                //Calling the getForumComment async task
                new doAsyncForumDetailsWork(loginResponse, forum).execute();
            }

        }


    }

    private class doAsyncDeleteCommentWork extends AsyncTask<String, Void, Boolean> {

        DataServices.AuthResponse loginResponse;
        long forumId;
        long commenId;
        String forums_error;

        doAsyncDeleteCommentWork(DataServices.AuthResponse loginResponse, long forumId, long commenId){
            this.loginResponse = loginResponse;
            this.forumId = forumId;
            this.commenId = commenId;
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            boolean isSuccess = false;
            try {
                DataServices.deleteComment(loginResponse.getToken(), forumId, commenId);
                isSuccess = true;
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
                forums_error = e.getMessage();
            }

            return isSuccess;
        }


        @Override
        protected void onPostExecute(Boolean comment) {
            super.onPostExecute(comment);
            if (comment == false){
                Toast.makeText(getActivity(), forums_error, Toast.LENGTH_SHORT).show();
                buttonPost.setEnabled(true);
                editTextComment.setEnabled(true);
            }else{
                //Calling the getForumComment async task
                new doAsyncForumDetailsWork(loginResponse, forum).execute();
            }

        }


    }

}