package com.example.homework2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class DisplayActivity extends AppCompatActivity {

    final public String displayTaskKey = "display_task";
    final public String removeFieldKey = "remove_field";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Task task = (Task) getIntent().getExtras().getSerializable(displayTaskKey);
        TextView name       = findViewById(R.id.textViewDisplayNameValue);
        TextView date       = findViewById(R.id.textViewDisplayDateValue);
        TextView priority   = findViewById(R.id.textViewDisplayPriorityValue);
        name.setText(task.getName());
        date.setText(new SimpleDateFormat("MM/dd/YYY").format(task.getDate()));
        priority.setText(task.getPriority());

        findViewById(R.id.buttonDisplayCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        findViewById(R.id.buttonDisplayDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DisplayActivity.this);
                builder.setTitle(R.string.delete_task)
                        .setMessage(R.string.delete_msg)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.putExtra(removeFieldKey, getIntent().getExtras().getInt(removeFieldKey));
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(DisplayActivity.this, R.string.no_action_msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.create().show();
            }
        });


    }
}