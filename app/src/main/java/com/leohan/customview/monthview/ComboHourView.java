package com.leohan.customview.monthview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.leohan.customview.R;
import com.leohan.customview.common.Conference;

import java.util.List;

/**
 * 三个HourView的组合控件，存放在ViewPager中
 * Created by Leo on 2016/8/12.
 */
public class ComboHourView extends LinearLayout {

    private static final String TAG = "MonthView";
    private Context context;
    private NoTimeHourView leftView;
    private NoTimeHourView centerView;
    private NoTimeHourView rightView;

    public ComboHourView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ComboHourView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.combo_hour_view, this);
        leftView = (NoTimeHourView) view.findViewById(R.id.leftView);
        centerView = (NoTimeHourView) view.findViewById(R.id.centerView);
        rightView = (NoTimeHourView) view.findViewById(R.id.rightView);
    }

    /**
     * 重新设置数据源
     */
    public void resetDataList(final List<Conference> list1, final List<Conference> list2, final List<Conference> list3) {
        leftView.resetDataList(list1);
        centerView.resetDataList(list2);
        rightView.resetDataList(list3);

        //hourView的点击事件，同时提供了增删改事件，详见HourView的public方法
        leftView.setOnItemClickListener(new NoTimeHourView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Conference conference) {
                Log.d(TAG, "onItemClick: " + list1.get(position).getDesc());
            }
        });
        centerView.setOnItemClickListener(new NoTimeHourView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Conference conference) {
                Log.d(TAG, "onItemClick: " + list2.get(position).getDesc());
            }
        });
        rightView.setOnItemClickListener(new NoTimeHourView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Conference conference) {
                Log.d(TAG, "onItemClick: " + list3.get(position).getDesc());
            }
        });
    }

}
