package com.leohan.hourview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HourView hourView;
    private List<Conference> list = new ArrayList<>();
    private Conference conference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hourView = (HourView) findViewById(R.id.hourView);

        conference = new Conference("t1", 2, 6);
        list.add(conference);
        conference = new Conference("t2", 3, 6);
        list.add(conference);
        conference = new Conference("t3", 2, 16);
        list.add(conference);
        conference = new Conference("t4", 0, 1);
        list.add(conference);
        conference = new Conference("t5", 0, 1);
        list.add(conference);
        conference = new Conference("t6", 0, 1);
        list.add(conference);
        conference = new Conference("t7", 14, 17);
        list.add(conference);
        hourView.setData(list);
    }
}
