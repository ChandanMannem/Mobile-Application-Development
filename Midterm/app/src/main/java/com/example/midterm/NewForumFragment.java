package com.example.midterm;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DataServices;

import java.util.ArrayList;
/*
    Assignment  : Midterm
    File Name   : NewForumFragment.java
    Name        : Chandan Mannem
 */

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewForumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewForumFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LOGIN_RESPONSE = "ARG_LOGIN_RESPONSE";

    // TODO: Rename and change types of parameters
    private DataServices.AuthResponse loginResponse;

    public NewForumFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NewForumFragment newInstance(DataServices.AuthResponse loginResponse) {
        NewForumFragment fragment = new NewForumFragment();
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

    NewForumListener newForumListener;
    EditText editTextTitle;
    EditText editTextDesc;
    Button buttonSubmit;
    Button buttonCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_forum, container, false);

        editTextTitle = view.findViewById(R.id.editTextForumTitle);
        editTextDesc = view.findViewById(R.id.editTextForumDesc);
        buttonSubmit = view.findViewById(R.id.buttonNewSubmit);
        buttonCancel = view.findViewById(R.id.buttonNewCancel);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String desc  = editTextDesc.getText().toString();

                new doNewForumsWork(loginResponse.getToken(), title, desc).execute();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newForumListener.onForumCancel(loginResponse);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof NewForumListener) {
            newForumListener = (NewForumListener) context;
        } else{
            throw new RuntimeException(context.toString() + " must implement LoginListener");
        }
    }

    public interface  NewForumListener{
        void onForumCancel(DataServices.AuthResponse loginResponse);
        void onForumSubmit(DataServices.AuthResponse loginResponse);
    }

    //AsyncTask
    private class doNewForumsWork extends AsyncTask<Void, Void, DataServices.Forum> {

        String token;
        String title;
        String desc;
        String newForum_error;

        doNewForumsWork(String token, String title, String desc){
            this.token = token;
            this.title = title;
            this.desc  = desc;
        }

        @Override
        protected DataServices.Forum doInBackground(Void... voids) {
            DataServices.Forum forum = null;

            try {
                forum = DataServices.createForum(token, title, desc);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
                newForum_error = e.getMessage();
            }

            return forum;
        }

        @Override
        protected void onPreExecute() {
            buttonSubmit.setEnabled(false);
            buttonCancel.setEnabled(false);
        }

        @Override
        protected void onPostExecute(DataServices.Forum forum) {
            if(forum == null){
                Toast.makeText(getActivity(), newForum_error, Toast.LENGTH_SHORT).show();
            }else{
                newForumListener.onForumSubmit(loginResponse);
            }
            buttonSubmit.setEnabled(true);
            buttonCancel.setEnabled(true);
        }


    }
}