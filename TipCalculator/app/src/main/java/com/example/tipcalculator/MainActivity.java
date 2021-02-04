package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.BlockingDeque;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
    String debug = "HW01";
    String error = "Enter Bill Total";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //On Click Listener
        findViewById(R.id.radioButton10).setOnClickListener(this);
        findViewById(R.id.radioButton15).setOnClickListener(this);
        findViewById(R.id.radioButton18).setOnClickListener(this);
        findViewById(R.id.radioButtonCustom).setOnClickListener(this);
        findViewById(R.id.buttonExit).setOnClickListener(this);


        SeekBar seekBarCustom = findViewById(R.id.seekBarCustom);
        TextView textViewProgress = findViewById(R.id.textViewProgress);

        RadioButton radioButtonCustom = findViewById(R.id.radioButtonCustom);
        RadioButton radioButton10 = findViewById(R.id.radioButton10);
        radioButton10.setChecked(true);



        //On key Listener
        EditText editTextDecimal = findViewById(R.id.editTextNumberDecimal);
        editTextDecimal.setOnKeyListener(this);

        seekBarCustom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                EditText editText = findViewById(R.id.editTextNumberDecimal);
                TextView textViewTip = findViewById(R.id.textViewTipResult);
                TextView textViewTotal = findViewById(R.id.textViewTotalResult);

                textViewProgress.setText(String.valueOf(progress + "%"));
                String editTextBill = editText.getText().toString();
                if (editTextBill.equals("")) {
                    textViewTip.setText("");
                    textViewTotal.setText("");
                } else {
                    Float billValue = Float.valueOf(editTextBill);
                    Log.d("progress Value: " + (progress * 0.01) , "onProgressChanged: ");
                    textViewTip.setText(String.valueOf(billValue * (progress * 0.01)));
                    textViewTotal.setText(String.valueOf(billValue + (billValue * (progress * 0.01)) ));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                radioButtonCustom.setChecked(true);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                EditText editText = findViewById(R.id.editTextNumberDecimal);
                TextView textViewTip = findViewById(R.id.textViewTipResult);
                TextView textViewTotal = findViewById(R.id.textViewTotalResult);

                String editTextBill = editText.getText().toString();
                if (editTextBill.equals("")) {
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    textViewTip.setText("");
                    textViewTotal.setText("");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.buttonExit) {
            Log.d(debug, "onClick: Exit button clicked");
        }

        EditText editText = findViewById(R.id.editTextNumberDecimal);
        String editTextBill = editText.getText().toString();
        TextView textViewTip = findViewById(R.id.textViewTipResult);
        TextView textViewTotal = findViewById(R.id.textViewTotalResult);

        if (!editTextBill.equals("")) {
            Float billValue = Float.valueOf(editTextBill);
            SeekBar seekBarCustom = findViewById(R.id.seekBarCustom);

            if (id == R.id.radioButton10) {
                Log.d(debug, "onClick: Radio Button 10% clicked");
                textViewTip.setText(String.valueOf(billValue * 0.10));
                textViewTotal.setText(String.valueOf(billValue + (billValue * 0.10)));
            } else if (id == R.id.radioButton15) {
                Log.d(debug, "onClick: Radio Button 15% clicked");
                textViewTip.setText(String.valueOf(billValue * 0.15));
                textViewTotal.setText(String.valueOf(billValue + (billValue * 0.15)));
            } else if (id == R.id.radioButton18) {
                Log.d(debug, "onClick: Radio Button 18% clicked");
                textViewTip.setText(String.valueOf(billValue * 0.18));
                textViewTotal.setText(String.valueOf(billValue + (billValue * 0.18)));
            } else if (id == R.id.radioButtonCustom) {
                Log.d(debug, "onClick: Radio Button custom clicked");
                int value = seekBarCustom.getProgress();
                textViewTip.setText(String.valueOf(billValue * (value * 0.01)));
                textViewTotal.setText(String.valueOf(billValue + (billValue * (value * 0.01))));
            }
        } else {
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            textViewTip.setText("");
            textViewTotal.setText("");
        }


    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        int id = v.getId();
        Log.d(debug, "onKey: ");
        if (id == R.id.editTextNumberDecimal) {
            EditText editText = findViewById(R.id.editTextNumberDecimal);
            TextView textViewTip = findViewById(R.id.textViewTipResult);
            TextView textViewTotal = findViewById(R.id.textViewTotalResult);
            SeekBar seekBarCustom = findViewById(R.id.seekBarCustom);

            String editTextBill = editText.getText().toString();
            if (editTextBill.equals("")) {
                textViewTip.setText("");
                textViewTotal.setText("");
            } else {
                RadioGroup radioGroup = findViewById(R.id.radioGroup);
                Float billValue = Float.valueOf(editTextBill);
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                float percentageValue = 0;

                if (radioButtonId == R.id.radioButton10) {
                    Log.d(debug, "onKey: Radio Button 10% clicked");
                    percentageValue = (float) (1 * 0.10);
                } else if (radioButtonId == R.id.radioButton15) {
                    Log.d(debug, "onKey: Radio Button 15% clicked");
                    percentageValue = (float) 0.15;
                } else if (radioButtonId == R.id.radioButton18) {
                    Log.d(debug, "onKey: Radio Button 18% clicked");
                    percentageValue = (float) 0.18;
                } else if( radioButtonId == R.id.radioButtonCustom){
                    Log.d(debug, "onKey: custom Button clicked");
                    percentageValue = (float) ((seekBarCustom.getProgress() * 0.01));
                }

                textViewTip.setText(String.valueOf(billValue * percentageValue));
                textViewTotal.setText(String.valueOf(billValue + (billValue * percentageValue)));
            }
        }
        return false;
    }

}