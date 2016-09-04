package com.example.santa.timeout;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimeViewComm time1 = (TimeViewComm) findViewById(R.id.time1);
        time1.startTime(22, 02, 14);

        TimeViewComm time2 = (TimeViewComm) findViewById(R.id.time2);
        time2.startTime(2, 11, 38);

        TimeViewComm time3 = (TimeViewComm) findViewById(R.id.time3);
        time3.startTime(2, 3, 34);

        TimeViewComm time4 = (TimeViewComm) findViewById(R.id.time4);
        time4.startTime(0, 23, 5);
        int min = 24;
        while (min-- > 0) {
            time4.addTimeoutPoint(0, min, 0);
        }
        time4.setOnTimeoutListener(new OnTimeoutListener() {
            @Override
            public void onTimePoint(String hour, String minute, String second) {
                Toast.makeText(MainActivity.this, "time4 has "+ hour+" : "+minute+" : "+second +" remaining", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTimeout() {
                Toast.makeText(MainActivity.this, "time4 is timeout", Toast.LENGTH_SHORT).show();
            }
        });

        TimeViewScroll time5 = (TimeViewScroll) findViewById(R.id.time5);
        time5.startTime(12, 13, 14);

        TimeViewScroll time6 = (TimeViewScroll) findViewById(R.id.time6);
        time6.startTime(28, 55, 33);
    }



}
