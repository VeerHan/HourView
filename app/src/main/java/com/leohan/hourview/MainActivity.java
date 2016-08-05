package com.leohan.hourview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private HourView hourView;
    private List<Conference> list = new ArrayList<>();
    private Conference conference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hourView = (HourView) findViewById(R.id.hourView);

        conference = new Conference(1, "t1", 2, 7);
        list.add(conference);
        conference = new Conference(2, "t2", 2, 6);
        list.add(conference);
        conference = new Conference(3, "t3", 5, 16);
        list.add(conference);
        conference = new Conference(4, "t4", 0, 1);
        list.add(conference);
        conference = new Conference(5, "t5", 1, 1);
        list.add(conference);
        conference = new Conference(6, "t6", 0, 1);
        list.add(conference);
        conference = new Conference(7, "t7", 7, 10);
        list.add(conference);
        conference = new Conference(8, "t8", 4.3f, 10);
        list.add(conference);
        conference = new Conference(9, "t9", 9, 13);
        list.add(conference);
        conference = new Conference(10, "t10", 9.5f, 15.8f);
        list.add(conference);
        conference = new Conference(11, "t11", 10.5f, 16.8f);
        list.add(conference);
        hourView.setDataList(list);

        List<Conference> data = hourView.getDataList();
        Log.d(TAG, "onCreate: data: " + data.toString());

        hourView.setOnItemClickListener(new HourView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Conference conference) {
                Toast.makeText(MainActivity.this, "position = " + position + ", desc = " + conference.getDesc(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onItemClick: " + "position = " + position + ", conference = " + conference.toString());
            }
        });
    }
}
