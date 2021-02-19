package com.example.inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
    Assignment  : InClass04
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

    RegisterFragment.RegisterAccountListener registerAccountListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register, container, false);

        editTextRegisterEmail = view.findViewById(R.id.editTextRegisterEmail);
        editTextRegisterName = view.findViewById(R.id.editTextRegisterName);
        editTextRegisterPassword = view.findViewById(R.id.editTextRegisterPassword);

        view.findViewById(R.id.buttonRegisterSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextRegisterEmail.getText().toString();
                String name = editTextRegisterName.getText().toString();
                String password = editTextRegisterPassword.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(name.equals("")){
                    Toast.makeText(getActivity(), R.string.name_error, Toast.LENGTH_SHORT).show();
                }else if (email.equals("") || !email.matches(emailPattern)){
                    Toast.makeText(getActivity(), R.string.email_error, Toast.LENGTH_SHORT).show();
                } else if (password.equals("")){
                    Toast.makeText(getActivity(), R.string.password_error, Toast.LENGTH_SHORT).show();
                } else{
                    DataServices.Account account = DataServices.register(name, email, password);
                    if(account == null){
                        Toast.makeText(getActivity(), R.string.register_error, Toast.LENGTH_SHORT).show();
                    }else{
                        registerAccountListener.registerAccount(account);
                    }
                }
            }
        });

        view.findViewById(R.id.buttonRegisterCancel).setOnClickListener(new View.OnClickListener() {
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
            registerAccountListener = (RegisterFragment.RegisterAccountListener) context;
        } else{
            throw new RuntimeException(context.toString() + " must implement UpdateAccountListener");
        }
    }

    public interface  RegisterAccountListener{
        void registerAccount(DataServices.Account user);
        void onRegisterCancel();
    }
}