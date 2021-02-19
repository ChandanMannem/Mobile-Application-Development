package com.example.inclass04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/*
    Assignment  : InClass04
    File Name   : MainActivity.java
    Name        : Chandan Mannem, Mahalavanya Sriram
 */
public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener,
                UpdateAccountFragment.UpdateAccountListener, AccountFragment.AccountListener,
                RegisterFragment.RegisterAccountListener {
    DataServices.Account account;
    private final String TAG = "TAG";
    private final String ACCOUNT_FRAGMENT = "ACCOUNT_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.login);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new LoginFragment())
                .commit();


    }

//    LoginFragment.LoginListener
    @Override
    public void setAccountDetails(DataServices.Account user) {
        account = user;
        setTitle(R.string.account);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, AccountFragment.newInstance(account.getName(), account.getEmail(), account.getPassword()),
                        ACCOUNT_FRAGMENT)
                .commit();
    }

    @Override
    public void onCreate() {
        setTitle(R.string.register_account);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new RegisterFragment())
                .commit();
    }

    //  UpdateAccountFragment
    @Override
    public void setUpdateAccount(DataServices.Account user) {
        account = user;
        setTitle(R.string.account);
        Log.d(TAG, "setUpdateAccount: "+account.getName());
        AccountFragment accountFragment =
                (AccountFragment) getSupportFragmentManager().findFragmentByTag(ACCOUNT_FRAGMENT);

        if(accountFragment != null){
            accountFragment.updateValues(account.getName(), account.getPassword());
        }
        getSupportFragmentManager().popBackStack();
    }

//     UpdateAccountFragment
    @Override
    public void onUpdateCancel() {
        setTitle(R.string.account);
        getSupportFragmentManager().popBackStack();
    }

    //    AccountListener
    @Override
    public void getAccountDetails(DataServices.Account user) {
        account = user;
        setTitle(R.string.update_account);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, UpdateAccountFragment.newInstance(account.getName(), account.getEmail(), account.getPassword()))
                .addToBackStack(null)
                .commit();
    }

//    AccountListener
    @Override
    public void isLogoutEnabled(boolean onClick) {
        if(onClick){
            account = null;
            setTitle(R.string.login);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new LoginFragment())
                    .commit();
        }
    }

//    RegisterAccountListener
    @Override
    public void registerAccount(DataServices.Account user) {
        account = user;
        setTitle(R.string.account);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, AccountFragment.newInstance(account.getName(),
                        account.getEmail(), account.getPassword()), ACCOUNT_FRAGMENT)
                .commit();
    }

//    RegisterAccountListener
    @Override
    public void onRegisterCancel() {
        setTitle(R.string.login);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }
}