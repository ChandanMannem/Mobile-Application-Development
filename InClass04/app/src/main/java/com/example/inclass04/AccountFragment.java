package com.example.inclass04;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/*
    Assignment  : InClass04
    File Name   : AccountFragment.java
    Name        : Chandan Mannem, Mahalavanya Sriram
 */

public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_NAME = "ARG_PARAM_NAME";
    private static final String ARG_PARAM_EMAIL = "ARG_PARAM_EMAIL";
    private static final String ARG_PARAM_PASSWORD = "ARG_PARAM_PASSWORD";
    private final String TAG = "TAG";

    // TODO: Rename and change types of parameters
    private String name;
    private String email;
    private String password;
    public  DataServices.Account account;

    public AccountFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String name, String email, String password) {
        AccountFragment fragment = new AccountFragment();
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

    TextView textViewAccountName;
    AccountFragment.AccountListener accountListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        textViewAccountName = view.findViewById(R.id.textViewAccountName);
        textViewAccountName.setText(this.name);

        view.findViewById(R.id.buttonAccountEditProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountListener.getAccountDetails(account);
            }
        });

        view.findViewById(R.id.buttonAccountLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountListener.isLogoutEnabled(true);
            }
        });

        return view;
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof AccountFragment.AccountListener) {
            accountListener = (AccountFragment.AccountListener) context;
        } else{
            throw new RuntimeException(context.toString() + " must implement LoginListener");
        }
    }

    public interface  AccountListener{
        void getAccountDetails(DataServices.Account user);
        void isLogoutEnabled(boolean onClick);
    }

    public void updateValues(String nameLocal, String passwordLocal){
        this.name = nameLocal;
        this.password = passwordLocal;

        account = new DataServices.Account(this.name, this.email, this.password);
    }


}