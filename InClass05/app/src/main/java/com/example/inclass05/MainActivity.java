package com.example.inclass05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        RegisterFragment.RegisterAccountListener,
        LoginFragment.LoginListener,
        AppCategoriesFragment.AppCategoriesFragmentListener,
        TopPaidFragment.TopPaidFragmentListener {

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
        setTitle(R.string.app_categories);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerId, AppCategoriesFragment.newInstance(token))
                .commit();
    }

    @Override
    public void onCreate() {
        setTitle(R.string.register_account);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerId, new RegisterFragment())
                .commit();
    }


    //RegisterAccountListener
    @Override
    public void registerSetToken(String token) {

        setTitle(R.string.app_categories);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerId, AppCategoriesFragment.newInstance(token))
                .commit();
    }


    @Override
    public void onRegisterCancel() {
        setTitle(R.string.login);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerId, new LoginFragment())
                .commit();
    }

    //RegisterAccountListener
    @Override
    public void setCategoryToken(ArrayList<DataServices.App> apps) {
        setTitle(R.string.top_paid);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerId, TopPaidFragment.newInstance(apps))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void Logout() {
        setTitle(R.string.login);
        this.token = null;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerId, new LoginFragment())
                .commit();
    }

    @Override
    public void setAppData(DataServices.App app) {
        setTitle(R.string.top_paid);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerId, AppDetailsFragment.newInstance(app))
                .addToBackStack(null)
                .commit();
    }
}