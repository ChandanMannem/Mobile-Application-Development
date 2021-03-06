package com.example.inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */

/*
    Assignment  : InClass04
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        editTextLoginEmail = view.findViewById(R.id.editTextLoginEmail);
        editTextLoginPassword = view.findViewById(R.id.editTextLoginPassword);



        view.findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextLoginEmail.getText().toString();
                String password = editTextLoginPassword.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(email.equals("") || !email.matches(emailPattern)){
                    Toast.makeText(getActivity(), R.string.email_error, Toast.LENGTH_SHORT).show();
                }else if (password.equals("")){
                    Toast.makeText(getActivity(), R.string.password_error, Toast.LENGTH_SHORT).show();
                }else{
                    DataServices.Account account = DataServices.login(email,password);
                    if(  account == null ){
                        Toast.makeText(getActivity(), R.string.login_error, Toast.LENGTH_SHORT).show();
                    }else{
                        loginListener.setAccountDetails(account);
                    }
                }
            }
        });

        view.findViewById(R.id.buttonCreate).setOnClickListener(new View.OnClickListener() {
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
        void setAccountDetails(DataServices.Account user);
        void onCreate();
    }
}