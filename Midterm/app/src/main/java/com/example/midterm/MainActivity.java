package com.example.midterm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.DataServices;

/*
    Assignment  : Midterm
    File Name   : MainActivity.java
    Name        : Chandan Mannem
 */

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener,
        RegisterFragment.RegisterAccountListener,
        FormsListFragment.FormsListListener,
        NewForumFragment.NewForumListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.login);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.ContainerId, new LoginFragment())
                .commit();
    }

    //    LoginFragment.LoginListener
    @Override
    public void setAuthResponse(DataServices.AuthResponse authResponse) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ContainerId, FormsListFragment.newInstance(authResponse))
                .commit();
    }

    @Override
    public void onCreate() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ContainerId, new RegisterFragment())
                .commit();
    }

    //RegisterAccountListener
    @Override
    public void setRegisterResponse(DataServices.AuthResponse reponse) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ContainerId, FormsListFragment.newInstance(reponse))
                .commit();
    }

    @Override
    public void onRegisterCancel() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ContainerId, new LoginFragment())
                .commit();
    }

    //FormsListListener
    @Override
    public void onLogout() {
        setTitle(R.string.login);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ContainerId, new LoginFragment())
                .commit();
    }

    @Override
    public void onCreateForum(DataServices.AuthResponse reponse) {

        setTitle(R.string.new_forum);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ContainerId, NewForumFragment.newInstance(reponse))
                .addToBackStack("NewForum")
                .commit();

    }

    @Override
    public void onForumCancel(DataServices.AuthResponse loginResponse) {
        setTitle(R.string.forums);
        FormsListFragment formsListFragment =
                (FormsListFragment) getSupportFragmentManager().findFragmentByTag("NewForum");

        if(formsListFragment != null){
            formsListFragment.updateValues(loginResponse);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onForumSubmit(DataServices.AuthResponse loginResponse) {
        setTitle(R.string.forums);
        FormsListFragment formsListFragment =
                (FormsListFragment) getSupportFragmentManager().findFragmentByTag("NewForum");

        if(formsListFragment != null){
            formsListFragment.updateValues(loginResponse);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onItemClickedMain(DataServices.AuthResponse loginResponse, DataServices.Forum forum) {
        setTitle(R.string.forum);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ContainerId, ForumDetailFragment.newInstance(loginResponse, forum))
                .addToBackStack("ListForum")
                .commit();

    }



}