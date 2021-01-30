/*
*
* Assignment - Inclass 02a
* File Name  - MainActivity.java
* Full Name  - Chandan Mannem, Mahalavanya Sriram
 */


package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    String debug = "Inclass-2a";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        findViewById(R.id.buttonToMeters).setOnClickListener(this);
        findViewById(R.id.buttonToFeet).setOnClickListener(this);
        findViewById(R.id.buttonToMiles).setOnClickListener(this);
        findViewById(R.id.buttonClear).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.d(debug, "onClick: ");
        EditText editTextInches = findViewById(R.id.editTextNumberDecimal);
        TextView textViewResult = findViewById(R.id.textViewResult);
        String editTextValue = String.valueOf(editTextInches.getText());

        if(editTextValue.equals("")) {
            if(v.getId() != R.id.buttonClear) Toast.makeText(context, R.string.edit_hint, Toast.LENGTH_SHORT).show();
        } else {
            float value = Float.valueOf(editTextValue);

            if(v.getId() == R.id.buttonToMeters){
                textViewResult.setText(String.valueOf(value * 0.0254) + " Miles");

            } else if(v.getId() == R.id.buttonToFeet){
                textViewResult.setText(String.valueOf(value / 12) + " Feet");

            } else if(v.getId() == R.id.buttonToMiles){
                textViewResult.setText(String.valueOf(value / 63360) + " Miles");
            } else{
                textViewResult.setText("");
                editTextInches.setText("");
            }
        }
    }
}