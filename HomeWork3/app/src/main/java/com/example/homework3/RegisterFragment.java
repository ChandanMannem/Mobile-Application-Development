package com.example.homework3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/*
    Assignment  : HomeWork3
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register, container, false);
        getActivity().setTitle(R.string.register_account);

        editTextRegisterEmail = view.findViewById(R.id.editTextRegisterEmail);
        editTextRegisterName = view.findViewById(R.id.editTextRegisterName);
        editTextRegisterPassword = view.findViewById(R.id.editTextRegisterPassword);

        view.findViewById(R.id.buttonRegisterSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextRegisterEmail.getText().toString();
                String name = editTextRegisterName.getText().toString();
                String password = editTextRegisterPassword.getText().toString();

                DataServices.register(name, email, password, new DataServices.AuthResponse() {
                        @Override
                        public void onSuccess(String token) {
                            registerAccountListener.registerSetToken(token);
                        }

                        @Override
                        public void onFailure(DataServices.RequestException exception) {

                            Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

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
            registerAccountListener = (RegisterAccountListener) context;
        } else{
            throw new RuntimeException(context.toString() + " must implement UpdateAccountListener");
        }
    }

    public interface  RegisterAccountListener{
        void registerSetToken(String token);
        void onRegisterCancel();
    }
}