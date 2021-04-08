package com.example.homework04;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


/*
    Assignment  : homework04
    File Name   : MainActivity.java
    Name        : Chandan Mannem, Mahalavanya Sriram
 */

public class MainActivity extends AppCompatActivity implements
        RegisterFragment.RegisterAccountListener,
        LoginFragment.LoginListener,
        AppCategoriesFragment.AppCategoriesFragmentListener,
        AppListFragment.AppListFragmentListener {

    private final String TAG = "TAG";
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.login);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerId, new LoginFragment())
                .commit();
    }


    //    LoginFragment.LoginListener
    @Override
    public void setToken(String token) {
        this.token = token;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerId, AppCategoriesFragment.newInstance(token))
                .commit();
    }

    @Override
    public void onCreate() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerId, new RegisterFragment())
                .commit();
    }


    //RegisterAccountListener
    @Override
    public void registerSetToken(String token) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerId, AppCategoriesFragment.newInstance(token))
                .commit();
    }


    @Override
    public void onRegisterCancel() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerId, new LoginFragment())
                .commit();
    }

    //RegisterAccountListener
    @Override
    public void setCategoryToken(ArrayList<DataServices.App> apps, String category) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerId, AppListFragment.newInstance(apps, category))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void Logout() {
        this.token = null;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerId, new LoginFragment())
                .commit();
    }

    @Override
    public void setAppData(DataServices.App app) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerId, AppDetailsFragment.newInstance(app))
                .addToBackStack(null)
                .commit();
    }

}