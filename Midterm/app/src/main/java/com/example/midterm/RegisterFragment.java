package com.example.midterm;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DataServices;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*
    Assignment  : Midterm
    File Name   : RegisterFragment.java
    Name        : Chandan Mannem
 */

public class RegisterFragment extends Fragment {

    EditText editTextRegisterEmail;
    EditText editTextRegisterName;
    EditText editTextRegisterPassword;
    Button buttonSubmit;
    Button buttonCancel;


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
        getActivity().setTitle(R.string.create_new_account);

        editTextRegisterEmail       = view.findViewById(R.id.editTextRegisterEmail);
        editTextRegisterName        = view.findViewById(R.id.editTextRegisterName);
        editTextRegisterPassword    = view.findViewById(R.id.editTextRegisterPassword);
        buttonSubmit                = view.findViewById(R.id.buttonRegisterSubmit);
        buttonCancel                = view.findViewById(R.id.buttonRegisterCancel);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextRegisterEmail.getText().toString();
                String name = editTextRegisterName.getText().toString();
                String password = editTextRegisterPassword.getText().toString();

                new doAsyncRegisterWork(name, email, password).execute();

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
        void setRegisterResponse(DataServices.AuthResponse reponse);
        void onRegisterCancel();
    }

    private class doAsyncRegisterWork extends AsyncTask<Void, Void, DataServices.AuthResponse> {

        String name;
        String email;
        String password;
        String register_error;

        doAsyncRegisterWork(String name, String email, String password){
            this.name = name;
            this.email = email;
            this.password = password;
        }

        @Override
        protected DataServices.AuthResponse doInBackground(Void... voids) {
            DataServices.AuthResponse regObj = null;
            try {
                regObj = DataServices.register(name, email, password);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
                register_error = e.getMessage();
            }
            return regObj;
        }

        @Override
        protected void onPreExecute() {
            buttonSubmit.setEnabled(false);
            buttonCancel.setEnabled(false);
        }

        @Override
        protected void onPostExecute(DataServices.AuthResponse authResponse) {
            super.onPostExecute(authResponse);
            if (authResponse == null){
                Toast.makeText(getActivity(), register_error, Toast.LENGTH_SHORT).show();
            }else{
                registerAccountListener.setRegisterResponse(authResponse);
            }

            buttonSubmit.setEnabled(true);
            buttonCancel.setEnabled(true);
        }


    }
}