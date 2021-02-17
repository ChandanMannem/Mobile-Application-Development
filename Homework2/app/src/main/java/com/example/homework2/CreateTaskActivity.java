package com.example.homework2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateTaskActivity extends AppCompatActivity {
    public static TextView dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        dateView = findViewById(R.id.textViewDateValue);

        findViewById(R.id.buttonSetDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new CreateTaskActivity.DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        //----------Submit Button
        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.editTextName);
                TextView textView = findViewById(R.id.textViewDateValue);
                RadioGroup radioGroup = findViewById(R.id.radioGroupCreateTask);
                String taskName = editText.getText().toString();
                String date = textView.getText().toString();
                int priority_id = radioGroup.getCheckedRadioButtonId();
                
                if(taskName.equals("")){
                    Toast.makeText(CreateTaskActivity.this, R.string.name_error, Toast.LENGTH_SHORT).show();
                } else if (date.equals("")){
                    Toast.makeText(CreateTaskActivity.this, R.string.date_error, Toast.LENGTH_SHORT).show();
                } else{
                    String priority_name = null;
                    if(priority_id == R.id.radioButtonHigh ){
                        priority_name = "High";
                    } else if (priority_id == R.id.radioButtonMedium){
                        priority_name = "Medium";
                    } else if(priority_id == R.id.radioButtonLow){
                        priority_name = "Low";
                    } else{
                        Toast.makeText(CreateTaskActivity.this, R.string.priority_error, Toast.LENGTH_SHORT).show();
                    }

                    if(priority_name != null){
                        Task task = null;
                        try {
                            Log.d("demo", "onClick: "+ date);
                            task = new Task(taskName, new SimpleDateFormat("MM/dd/yyyy").parse(date), priority_name);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Toast.makeText(CreateTaskActivity.this, R.string.date_format_error, Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent();
                        intent.putExtra("task", task);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                }
            }
        });

        //----------Close Button
        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

//            view.setMinDate(System.currentTimeMillis()-1000);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            String date = new SimpleDateFormat("MM/dd/YYYY").format(cal.getTime());
            Log.d("demo/", date);
            dateView.setText(date);
        }
    }
}