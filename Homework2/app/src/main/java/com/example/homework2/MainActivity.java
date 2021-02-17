package com.example.homework2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.BlockingDeque;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Task> tasks;
    final String TAG = "HW02";
    final public String displayTaskKey = "display_task";
    final public String removeFieldKey = "remove_field";

    final static public int REQ_CODE_CreateTask = 100;
    final static public int REQ_CODE_DisplayTask = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks = new ArrayList<>();
        TextView textViewTaskName = findViewById(R.id.textViewTaskName);
        textViewTaskName.setText(R.string.none);

        //------For View Tasks
        findViewById(R.id.buttonViewTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strArray[] = new String[tasks.size()];
                int index = 0;
                for(Task task: tasks){
                    strArray[index] = task.getName();
                    index++;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.select_task)
                        .setItems(strArray, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                                intent.putExtra(displayTaskKey, tasks.get(which));
                                intent.putExtra(removeFieldKey, which);
                                startActivityForResult(intent, REQ_CODE_DisplayTask);
                            }
                        });
                builder.create().show();
            }
        });

        //------For Create Tasks
        findViewById(R.id.buttonCreateTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                startActivityForResult(intent,  REQ_CODE_CreateTask);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");

        if(requestCode == REQ_CODE_CreateTask && resultCode == RESULT_OK && data != null){
            Task task = (Task) data.getExtras().getSerializable("task");
            tasks.add(task);
        }else if(requestCode == REQ_CODE_DisplayTask && resultCode == RESULT_OK && data != null){
            Log.d(TAG, "onActivityResult: "+ data.getExtras().getInt(removeFieldKey));
            tasks.remove(data.getExtras().getInt(removeFieldKey));
            Toast.makeText(this, R.string.delete_success, Toast.LENGTH_SHORT).show();
        }

        TextView title = findViewById(R.id.textViewTitle);
        TextView textViewTaskName = findViewById(R.id.textViewTaskName);
        TextView textViewDate = findViewById(R.id.textViewDate);
        TextView textViewPrio = findViewById(R.id.textViewPrio);

        title.setText("You have " + tasks.size() + " task");
        if(tasks.size() != 0){
            Collections.sort(tasks, new Comparator<Task>() {
                @Override
                public int compare(Task o1, Task o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
            textViewTaskName.setText(tasks.get(0).getName());
            textViewDate.setText(new SimpleDateFormat("MM/dd/YYY").format(tasks.get(0).getDate()));
            textViewPrio.setText(tasks.get(0).getPriority());
        }else{
            textViewTaskName.setText(R.string.none);
            textViewDate.setText("");
            textViewPrio.setText("");
        }

    }
}