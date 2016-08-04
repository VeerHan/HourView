package com.leohan.hourview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by lei.han on 2016/8/3.
 */

public class HourView extends LinearLayout {

    private static final String TAG = "HourView";
    //左侧时间列表TextView的宽度，单位px
    private int leftMargin;
    //右侧整块区域，用于添加显示时间方块
    private AbsoluteLayout contentLayout;
    //右侧整块AbsoluteLayout的宽度和高度，单位px
    private int contentWidth, contentHeight;
    private List<Conference> list;
    private Context context;
    private StartTimeComparator startTimeComparator;
    private UpdateTimeComparator updateTimeComparator;
    //左边TextView的宽、高，单位dp
    private int leftWidth = 80;
    private int leftHeight = 60;
    //分割线的高度，单位dp
    private int lineHeight = 1;

    public HourView(Context context) {
        super(context, null);
    }

    public HourView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.hour_view, this);
        contentLayout = (AbsoluteLayout) view.findViewById(R.id.contentLayout);
        leftMargin = DimenUtil.dp2px(context, leftWidth);
        contentWidth = DimenUtil.getScreenWidth(context) - leftMargin;
        contentHeight = DimenUtil.dp2px(context, leftHeight * 24 + 23);
//        Log.d(TAG, "leftMargin = " + leftMargin + " & contentLayout :contentWidth = " + contentWidth + ", contentHeight = " + contentHeight);
        startTimeComparator = new StartTimeComparator();
        updateTimeComparator = new UpdateTimeComparator();
    }

    /**
     * 设置数据源
     *
     * @param list
     */
    public void setData(List<Conference> list) {
        this.list = list;
        Collections.sort(list, startTimeComparator);
        Log.d(TAG, "sort list = " + list.toString());
        sortList(list);
    }

    private void sortList(List<Conference> list) {
        //当list里面只有一个元素时直接画出来
        if (list != null & list.size() == 1) {
            Log.d(TAG, "list.size() == 1 list = " + list.toString());
            Conference conference = list.get(0);
            addView(conference, 0, 0);
        }
        if (list != null & list.size() > 1) {
            Log.d(TAG, "list.size() > 1");
            for (int i = 0; i < list.size() - 1; i++) {
                int j = 0;
                while (list.get(j).getEndTime() <= list.get(i + 1).getStartTime()) {
                    j++;
                    if (j > i) {
                        List<Conference> subList = list.subList(i + 1, list.size());
                        Log.d(TAG, "sortList: subList = " + subList.toString());
                        sortList(subList);
                        //add view
                        for (int k = 0; k <= i; k++) {
                            //Collections.sort(list, updateTimeComparator);
                            Conference conference = list.get(k);
                            addView(conference, i, k);
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * 添加TextView的方法
     */
    private void addView(Conference conference, int i, int k) {
        long startTime = conference.getStartTime();
        long endTime = conference.getEndTime();
        int width = contentWidth / (i + 1);
        int height = DimenUtil.dp2px(context, (int) (endTime - startTime) * (leftHeight + lineHeight));
        int x = width * k;
        int y = DimenUtil.dp2px(context, (int) startTime * (leftHeight + lineHeight));
        contentLayout.addView(getTextView(conference), new AbsoluteLayout.LayoutParams(width, height, x, y));
    }

    /**
     * 获取数据源
     */
    public List<Conference> getData() {
        return list;
    }

    /**
     * 当数据源改变时调用此方法刷新界面
     */
    public void notifyDataSetChanged() {
        setData(list);
    }

    @NonNull
    private TextView getTextView(Conference conference) {
        TextView textView = new TextView(context);
        textView.setText(conference.getDesc());
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "onClick", Toast.LENGTH_SHORT).show();
            }
        });
        return textView;
    }
}
