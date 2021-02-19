package com.example.inclass04;

import android.accounts.Account;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
/*
    Assignment  : InClass04
    File Name   : UpdateAccountFragment.java
    Name        : Chandan Mannem, Mahalavanya Sriram
 */

public class UpdateAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_NAME = "ARG_PARAM_NAME";
    private static final String ARG_PARAM_EMAIL = "ARG_PARAM_EMAIL";
    private static final String ARG_PARAM_PASSWORD = "ARG_PARAM_PASSWORD";

    // TODO: Rename and change types of parameters
    private String name;
    private String email;
    private String password;
    public  DataServices.Account account;

    public UpdateAccountFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static UpdateAccountFragment newInstance(String name, String email, String password) {
        UpdateAccountFragment fragment = new UpdateAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_NAME, name);
        args.putString(ARG_PARAM_EMAIL, email);
        args.putString(ARG_PARAM_PASSWORD, password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.name = getArguments().getString(ARG_PARAM_NAME);
            this.email = getArguments().getString(ARG_PARAM_EMAIL);
            this.password = getArguments().getString(ARG_PARAM_EMAIL);

            account = new DataServices.Account(getArguments().getString(ARG_PARAM_NAME), getArguments().getString(ARG_PARAM_EMAIL), getArguments().getString(ARG_PARAM_EMAIL));

        }
    }

    TextView textViewUpdateEmail;
    EditText editTextUpdateName;
    EditText editTextUpdatePassword;
    UpdateAccountFragment.UpdateAccountListener updateListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_account, container, false);
        textViewUpdateEmail = view.findViewById(R.id.textViewUpdateEmail);
        textViewUpdateEmail.setText(email);

        editTextUpdateName = view.findViewById(R.id.editTextUpdateName);
        editTextUpdatePassword = view.findViewById(R.id.editTextUpdatePassword);

        editTextUpdateName.setText(this.name);
        editTextUpdatePassword.setText(this.password);

        view.findViewById(R.id.buttonUpdateSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameLocal = editTextUpdateName.getText().toString();
                String passwordLocal = editTextUpdatePassword.getText().toString();

                if(nameLocal.equals("")){
                    Toast.makeText(getActivity(), R.string.name_error, Toast.LENGTH_SHORT).show();
                }else if (passwordLocal.equals("")){
                    Toast.makeText(getActivity(), R.string.password_error, Toast.LENGTH_SHORT).show();
                }else{

                    DataServices.Account newAccount = DataServices.update(account, nameLocal, passwordLocal);
                    if(newAccount == null){
                        Toast.makeText(getActivity(),R.string.update_error, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),R.string.update_success, Toast.LENGTH_SHORT).show();
                        DataServices.update(account, newAccount.getName(), newAccount.getPassword());
                        updateListener.setUpdateAccount(newAccount);
                    }

                }
            }
        });

        view.findViewById(R.id.buttonUpdateCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateListener.onUpdateCancel();
            }
        });


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof UpdateAccountFragment.UpdateAccountListener) {
            updateListener = (UpdateAccountFragment.UpdateAccountListener) context;
        } else{
            throw new RuntimeException(context.toString() + " must implement UpdateAccountListener");
        }
    }

    public interface  UpdateAccountListener{
        void setUpdateAccount(DataServices.Account user);
        void onUpdateCancel();
    }
}