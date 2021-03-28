package com.example.midterm;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.DataServices;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
/*
    Assignment  : Midterm
    File Name   : FormsListFragment.java
    Name        : Chandan Mannem
 */
public class FormsListFragment extends Fragment implements FormsListRecyclerView.InteractWithRecyclerView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LOGIN_RESPONSE = "ARG_LOGIN_RESPONSE";

    // TODO: Rename and change types of parameters
    private DataServices.AuthResponse loginResponse;


    // TODO: Rename and change types and number of parameters
    public static FormsListFragment newInstance(DataServices.AuthResponse loginResponse) {
        FormsListFragment fragment = new FormsListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOGIN_RESPONSE, loginResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loginResponse = (DataServices.AuthResponse) getArguments().getSerializable(ARG_LOGIN_RESPONSE);
        }
    }

    Button buttonLogout;
    Button buttonNewForum;
    RecyclerView recyclerView;
    FormsListListener formsListListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_forms_list, container, false);

        buttonLogout        = view.findViewById(R.id.buttonFormsLogout);
        buttonNewForum      = view.findViewById(R.id.buttonNewForum);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formsListListener.onLogout();
            }
        });

        buttonNewForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formsListListener.onCreateForum(loginResponse);
            }
        });

        recyclerView = view.findViewById(R.id.forumsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        new doAsyncForumsWork(loginResponse).execute();

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FormsListListener) {
            formsListListener = (FormsListListener) context;
        } else{
            throw new RuntimeException(context.toString() + " must implement LoginListener");
        }
    }

    @Override
    public void onLike(boolean isLiked, long formId) {
        Log.d("demo", "callFavAsync: coming here");
        new doAsyncLikeDisLike(loginResponse,isLiked, formId).execute();
    }

    @Override
    public void getForumDetails(DataServices.AuthResponse loginResponse, long forumId) {
        new doAsyncDeleteWork(loginResponse, forumId).execute();
    }

    @Override
    public void onItemClicked(DataServices.AuthResponse loginResponse, DataServices.Forum forum) {
        formsListListener.onItemClickedMain(loginResponse, forum);
    }

    public interface  FormsListListener{
        void onLogout();
        void onCreateForum(DataServices.AuthResponse loginResponse);
        void onItemClickedMain(DataServices.AuthResponse loginResponse, DataServices.Forum forum);
    }

    public void updateValues(DataServices.AuthResponse loginResponse){
        this.loginResponse =loginResponse;
    }

    //AsyncTask
    private class doAsyncForumsWork extends AsyncTask<Void, Void, ArrayList<DataServices.Forum>> {

        DataServices.AuthResponse loginResponse;
        String forums_error;

        doAsyncForumsWork(DataServices.AuthResponse loginResponse){
            this.loginResponse = loginResponse;
        }

        @Override
        protected ArrayList<DataServices.Forum> doInBackground(Void... voids) {
            ArrayList<DataServices.Forum> forums = null;

            try {
                forums = DataServices.getAllForums(loginResponse.getToken());
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
                forums_error = e.getMessage();
            }

            return forums;
        }

        @Override
        protected void onPreExecute() {
            buttonLogout.setEnabled(false);
            buttonNewForum.setEnabled(false);
        }

        @Override
        protected void onPostExecute(ArrayList<DataServices.Forum> forums) {
            super.onPostExecute(forums);
            if (forums == null){
                Toast.makeText(getActivity(), forums_error, Toast.LENGTH_SHORT).show();
            }else{

                FormsListRecyclerView adapter = new FormsListRecyclerView(forums,
                        FormsListFragment.this, loginResponse);
                recyclerView.setAdapter(adapter);
            }

            buttonLogout.setEnabled(true);
            buttonNewForum.setEnabled(true);
        }


    }

    private class doAsyncLikeDisLike extends AsyncTask<Void, Void, Boolean> {

        DataServices.AuthResponse loginResponse;
        String forums_error;
        Boolean isLiked;
        long formId;

        doAsyncLikeDisLike(DataServices.AuthResponse loginResponse, Boolean isLiked, long formId){
            this.loginResponse = loginResponse;
            this.isLiked = isLiked;
            this.formId = formId;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            boolean isSuccess = false;
            try {
                if(isLiked){
                    DataServices.likeForum(loginResponse.getToken(), formId);
                }else {
                    DataServices.unLikeForum(loginResponse.getToken(), formId);
                }
                isSuccess = true;
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
                forums_error = e.getMessage();
            }

            return isSuccess;
        }

        @Override
        protected void onPreExecute() {
            buttonLogout.setEnabled(false);
            buttonNewForum.setEnabled(false);
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if (isSuccess == false){
                Toast.makeText(getActivity(), forums_error, Toast.LENGTH_SHORT).show();
            }else{
                new doAsyncForumsWork(loginResponse).execute();
            }

        }


    }

    private class doAsyncDeleteWork extends AsyncTask<Void, Void, Boolean> {

        DataServices.AuthResponse loginResponse;
        String forums_error;
        long formId;

        doAsyncDeleteWork(DataServices.AuthResponse loginResponse, long formId){
            this.loginResponse = loginResponse;
            this.formId = formId;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            boolean isSuccess = false;
            try {
                DataServices.deleteForum(loginResponse.getToken(), formId);
                isSuccess = true;
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
                forums_error = e.getMessage();
            }

            return isSuccess;
        }

        @Override
        protected void onPreExecute() {
            buttonLogout.setEnabled(false);
            buttonNewForum.setEnabled(false);
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if (isSuccess == false){
                Toast.makeText(getActivity(), forums_error, Toast.LENGTH_SHORT).show();
            }else{
                new doAsyncForumsWork(loginResponse).execute();
            }

        }


    }
}