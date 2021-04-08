package com.example.homework04;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */

/*
    Assignment  : homework04
    File Name   : LoginFragment.java
    Name        : Chandan Mannem, Mahalavanya Sriram
 */

public class LoginFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    EditText editTextLoginEmail;
    EditText editTextLoginPassword;
    Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle(R.string.login);

        editTextLoginEmail      = view.findViewById(R.id.editTextLoginEmail);
        editTextLoginPassword   = view.findViewById(R.id.editTextLoginPassword);
        Button buttonLogin      = view.findViewById(R.id.buttonLogin);
        Button buttonCreate     = view.findViewById(R.id.buttonCreate);

        //--Handler for Thread
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                switch (msg.what){
                    case doLoginWork.STATUS_START:
                        buttonLogin.setEnabled(false);
                        buttonCreate.setEnabled(false);
                        break;
                    case doLoginWork.STATUS_SUCCESS:
                        loginListener.setToken((String)msg.obj);
                        break;
                    case doLoginWork.STATUS_FAILURE:
                        Toast.makeText(getActivity(), (String)msg.obj, Toast.LENGTH_SHORT).show();
                        break;
                    case doLoginWork.STATUS_STOP:
                        buttonLogin.setEnabled(true);
                        buttonCreate.setEnabled(true);
                        break;
                }
                return false;
            }
        });

        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextLoginEmail.getText().toString();
                String password = editTextLoginPassword.getText().toString();

                threadPool.execute(new doLoginWork(email,password));

            }
        });

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginListener.onCreate();
            }
        });

        return view;
    }
    LoginListener loginListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof  LoginListener) {
            loginListener = (LoginListener) context;
        } else{
            throw new RuntimeException(context.toString() + " must implement LoginListener");
        }
    }

    public interface  LoginListener{
        void setToken(String token);
        void onCreate();
    }

    //--------Thread
    private class doLoginWork implements Runnable{
        private String email;
        private String password;

        public static final int STATUS_START   = 0x00;
        public static final int STATUS_SUCCESS = 0x01;
        public static final int STATUS_FAILURE = 0x02;
        public static final int STATUS_STOP    = 0x03;

        doLoginWork(String email, String password){

            this.email = email;
            this.password = password;
        }
        @Override
        public void run() {
            Message message = new Message();
            message.what = STATUS_START;
            handler.sendMessage(message);

            try {
                String token = DataServices.login(email,password);
                message = new Message();
                message.what = STATUS_SUCCESS;
                message.obj  = token;
                handler.sendMessage(message);
            } catch (DataServices.RequestException e) {
                message = new Message();
                message.what = STATUS_FAILURE;
                message.obj  = e.getMessage();
                handler.sendMessage(message);
            }

            message = new Message();
            message.what = STATUS_STOP;
            handler.sendMessage(message);

        }
    }
}