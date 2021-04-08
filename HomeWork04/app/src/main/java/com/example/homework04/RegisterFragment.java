package com.example.homework04;

import android.content.Context;
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    Assignment  : HomeWork04
    File Name   : RegisterFragment.java
    Name        : Chandan Mannem, Mahalavanya Sriram
 */

public class RegisterFragment extends Fragment {

    EditText editTextRegisterEmail;
    EditText editTextRegisterName;
    EditText editTextRegisterPassword;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    RegisterAccountListener registerAccountListener;
    Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register, container, false);
        getActivity().setTitle(R.string.register_account);

        editTextRegisterEmail       = view.findViewById(R.id.editTextRegisterEmail);
        editTextRegisterName        = view.findViewById(R.id.editTextRegisterName);
        editTextRegisterPassword    = view.findViewById(R.id.editTextRegisterPassword);
        Button buttonSubmit         = view.findViewById(R.id.buttonRegisterSubmit);
        Button buttonCancel         = view.findViewById(R.id.buttonRegisterCancel);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                switch (msg.what){
                    case doRegisterWork.STATUS_START:
                        buttonSubmit.setEnabled(false);
                        buttonCancel.setEnabled(false);
                        break;
                    case doRegisterWork.STATUS_SUCCESS:
                        registerAccountListener.registerSetToken((String)msg.obj);
                        break;
                    case doRegisterWork.STATUS_FAILURE:
                        Toast.makeText(getActivity(), (String)msg.obj, Toast.LENGTH_SHORT).show();
                        break;
                    case doRegisterWork.STATUS_STOP:
                        buttonSubmit.setEnabled(true);
                        buttonCancel.setEnabled(true);
                        break;
                }
                return false;
            }
        });

        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextRegisterEmail.getText().toString();
                String name = editTextRegisterName.getText().toString();
                String password = editTextRegisterPassword.getText().toString();

                threadPool.execute(new doRegisterWork(name, email,password));

            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccountListener.onRegisterCancel();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof RegisterAccountListener){
            registerAccountListener = (RegisterAccountListener) context;
        } else{
            throw new RuntimeException(context.toString() + " must implement UpdateAccountListener");
        }
    }

    public interface  RegisterAccountListener{
        void registerSetToken(String token);
        void onRegisterCancel();
    }

    private class doRegisterWork implements Runnable{
        private  String name;
        private String email;
        private String password;

        public static final int STATUS_START   = 0x00;
        public static final int STATUS_SUCCESS = 0x01;
        public static final int STATUS_FAILURE = 0x02;
        public static final int STATUS_STOP    = 0x03;

        doRegisterWork(String name, String email, String password){
            this.name = name;
            this.email = email;
            this.password = password;
        }
        @Override
        public void run() {
            Message message = new Message();
            message.what = STATUS_START;
            handler.sendMessage(message);

            try {
                String token = DataServices.register(name, email, password);
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