package com.leohan.customview.hourview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.leohan.customview.common.Conference;
import com.leohan.customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * HourView Demo
 *
 * @author lei.han
 * @time 2016/8/4
 */
public class HourViewActivity extends AppCompatActivity {

    private static final String TAG = "HourViewActivity";
    private HourView hourView;
    private List<Conference> list = new ArrayList<>();
    private Conference conference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hour_view);
        hourView = (HourView) findViewById(R.id.hourView);

        //添加测试数据
        addTestData();
        //绑定数据源
        hourView.setDataList(list);

        //获取数据源
        List<Conference> data = hourView.getDataList();
        Log.d(TAG, "onCreate: data: " + data.toString());

        //hourView的点击事件，同时提供了增删改事件，详见HourView的public方法
        hourView.setOnItemClickListener(new HourView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Conference conference) {
                Log.d(TAG, "onItemClick: " + "position = " + position + ", conference = " + conference.toString());
                //点击该元素将其删除，仅做测试
                hourView.remove(conference);
            }
        });
    }

    /**
     * 添加测试数据
     */
    private void addTestData() {
        conference = new Conference(1, "t1", 2, 7);
        list.add(conference);
        conference = new Conference(2, "T2", 2, 6);
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
    }
}