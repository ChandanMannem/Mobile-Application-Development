/*
* Assignment: In Class 02b
* File Name: MainActivity.java
* File : Chandan Mannem, Mahalavanya Sriram
 */


package com.example.inclass02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String debug = "Inclass-02:";
    String error = "Enter Inch Value";
    String negativeError = "Enter Only Postive Values";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context parent = this;

        TextView textViewResult = findViewById(R.id.textViewResult);

        Button buttonConvert = findViewById(R.id.buttonConvert);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextInches = findViewById(R.id.editTextNumberDecimal);
                String editTextValue = String.valueOf(editTextInches.getText());

                if(!editTextValue.equals("")){

                    float value = Float.valueOf(editTextValue);
                    if(value < 0){
                        Toast.makeText(parent,negativeError,Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.d(debug, value + "");
                    int checkedId = radioGroup.getCheckedRadioButtonId();
                    if(checkedId == R.id.radioButtonToMeters){
                        textViewResult.setText(String.valueOf(value * 0.0254) + " Meters");
                        Log.d(debug, "onClick: Meters");

                    } else if(checkedId == R.id.radioButtonToFeet){
                        textViewResult.setText(String.valueOf(value/12) + " Feet");
                        Log.d(debug, "onClick: Feet");

                    } else if(checkedId == R.id.radioButtonToMiles){
                        textViewResult.setText(String.valueOf(value /63360) + " Miles");
                        Log.d(debug, "onClick: Miles");

                    } else if(checkedId == R.id.radioButtonClear){
                        Log.d(debug, "onClick: Clear");
                        textViewResult.setText("");
                        editTextInches.setText("");

                    }
                } else {
                    Toast.makeText(parent,error,Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}