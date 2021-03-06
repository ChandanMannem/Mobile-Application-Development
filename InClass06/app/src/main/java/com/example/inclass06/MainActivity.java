package com.example.inclass06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "InClass06";
    public int complexity;
    ProgressBar progressBar;
    TextView textViewProgressValue;
    TextView textViewAverageValue;
    ListView listView;
    Button buttonThread;
    Button buttonAsync;
    ArrayAdapter<Double> adapter;
    ArrayList<Double> list;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.main_activity);

        //--Find View by Id's
        progressBar             = findViewById(R.id.progressBar);
        textViewProgressValue   = findViewById(R.id.textViewProgress);
        textViewAverageValue    = findViewById(R.id.textViewAverageValue);
        buttonThread            = findViewById(R.id.buttonThread);
        buttonAsync             = findViewById(R.id.buttonAsync);
        listView                = findViewById(R.id.listView);


        list     = new ArrayList<>();
        adapter  = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list);

        TextView textViewComplexityValue = findViewById(R.id.textViewComplexityValue);
        SeekBar seekBar = findViewById(R.id.seekBar);

        //--SeekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewComplexityValue.setText(progress + " Times");
                complexity = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //--Handler for Thread
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                switch (msg.what){
                    case doWork.STATUS_START:
                        initializeValues();
                        break;
                    case doWork.STATUS_PROGRESS:
                        onProgress(msg.getData().getInt(doWork.PROGRESS), msg.getData().getDouble(doWork.RANDOM_NUM), msg.getData().getDouble(doWork.SUM));
                        break;
                    case doWork.STATUS_STOP:
                        onStop((double) msg.obj);
                        break;
                }
                return false;
            }
        });

        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        //--Thread Button
        buttonThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Button Thread Pressed");
                progressBar.setMax(complexity);
                threadPool.execute(new doWork(complexity));

            }
        });

        //--Async Button
        buttonAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Button Async Pressed");
                progressBar.setMax(complexity);
                new doAsyncWork().execute(complexity);
            }
        });
    }

    private void onProgress(int anInt, double aDouble, double aDouble1) {
        progressBar.setProgress((int) anInt);
        textViewProgressValue.setText(anInt + "/" + complexity);
        textViewAverageValue.setText("Average: " + String.valueOf(aDouble/anInt));
        list.add(aDouble1);
        Log.d(TAG, "onProgressUpdate: " + list.toString());
        adapter.notifyDataSetChanged();
    }

    private void initializeValues() {
        listView.setAdapter(adapter);
        list.clear();
        adapter.notifyDataSetChanged();
        textViewProgressValue.setText("0/"+complexity);
        textViewAverageValue.setText("");
        progressBar.setProgress(0);
        buttonThread.setEnabled(false);
        buttonAsync.setEnabled(false);
    }

    private void onStop(double value){
        textViewAverageValue.setText("Average: " + value);
        buttonThread.setEnabled(true);
        buttonAsync.setEnabled(true);
    }


    private class doWork implements Runnable{
        int val;
        public static final int STATUS_START = 0x00;
        public static final int STATUS_PROGRESS = 0x01;
        public static final int STATUS_STOP = 0x02;
        public static final String PROGRESS     = "PROGRESS";
        public static final String RANDOM_NUM   = "RANDOM_NUM";
        public static final String SUM          = "SUM";

        doWork(int value){
            val = value;
        }
        @Override
        public void run() {

            double sumNumber = Double.MIN_VALUE;

            //--Start
            Message message = new Message();
            message.what = STATUS_START;
            handler.sendMessage(message);

            //--Progress
            for(int index = 0; index<val; index++){
                double randomNumber = HeavyWork.getNumber();
                sumNumber += randomNumber;

                message = new Message();
                message.what = STATUS_PROGRESS;
                Bundle bundle = new Bundle();
                bundle.putInt(PROGRESS, index+1);
                bundle.putDouble(RANDOM_NUM, randomNumber);
                bundle.putDouble(SUM, sumNumber);
                message.setData(bundle);
                handler.sendMessage(message);
            }

            //--Stop
            message = new Message();
            message.what = STATUS_STOP;
            message.obj = sumNumber/val;
            handler.sendMessage(message);

        }
    }


    private class doAsyncWork extends AsyncTask<Integer, Double, Double>{

        @Override
        protected void onPreExecute() {
            initializeValues();
        }

        @Override
        protected Double doInBackground(Integer... integers) {

            if(integers[0] == 0) return (double)0;

            double sumNumber = Double.MIN_VALUE;
            double count = 0;
            Log.d(TAG, "doInBackground: " + Arrays.toString(integers));
            for(int index = 0; index<integers[0]; index++){
                double randomNumber = HeavyWork.getNumber();
                sumNumber += randomNumber;
                count++;
                publishProgress(count, randomNumber, sumNumber);
            }

            return sumNumber/integers[0];
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            onStop(aDouble);
        }

        @Override
        protected void onProgressUpdate(Double... values) {
            onProgress((int)Math.round(values[0]), values[1], values[2]);
        }

    }
}