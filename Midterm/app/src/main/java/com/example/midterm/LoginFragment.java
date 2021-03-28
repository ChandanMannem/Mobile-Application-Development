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
/*
    Assignment  : Midterm
    File Name   : LoginFragment.java
    Name        : Chandan Mannem
 */
public class LoginFragment extends Fragment {

    final String TAG = "MidTerm";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    EditText editTextLoginEmail;
    EditText editTextLoginPassword;
    Button   buttonLogin;
    Button   buttonCreate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle(R.string.login);

        editTextLoginEmail    = view.findViewById(R.id.editTextLoginEmail);
        editTextLoginPassword = view.findViewById(R.id.editTextLoginPassword);
        buttonLogin           = view.findViewById(R.id.buttonLogin);
        buttonCreate          = view.findViewById(R.id.buttonNewCancel);

        view.findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextLoginEmail.getText().toString();
                String password = editTextLoginPassword.getText().toString();
                new doAsyncLoginWork(email, password).execute();
            }
        });

        view.findViewById(R.id.buttonNewCancel).setOnClickListener(new View.OnClickListener() {
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
        void setAuthResponse(DataServices.AuthResponse authResponse);
        void onCreate();
    }

    private class doAsyncLoginWork extends AsyncTask<Void, Void, DataServices.AuthResponse> {

        String email;
        String password;
        String login_error;
        
        doAsyncLoginWork(String email, String password){
            this.email = email;
            this.password = password;
        }

        @Override
        protected DataServices.AuthResponse doInBackground(Void... voids) {
            DataServices.AuthResponse loginObj = null;
            try {
                loginObj = DataServices.login(email,password);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
                login_error = e.getMessage();
            }
            return loginObj;
        }

        @Override
        protected void onPreExecute() {
            buttonLogin.setEnabled(false);
            buttonCreate.setEnabled(false);
        }

        @Override
        protected void onPostExecute(DataServices.AuthResponse authResponse) {
            super.onPostExecute(authResponse);
            if (authResponse == null){
                Toast.makeText(getActivity(), login_error, Toast.LENGTH_SHORT).show();
            }else{
                loginListener.setAuthResponse(authResponse);
            }

            buttonCreate.setEnabled(true);
            buttonLogin.setEnabled(true);
        }


    }
}