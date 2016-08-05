package com.leohan.hourview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自定义日视图
 * Created by lei.han on 2016/8/3.
 */

public class HourView extends LinearLayout {

    /**
     * 每个元素的点击事件接口
     */
    interface OnItemClickListener {
        void onItemClick(int position, Conference conference);
    }

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
    //左边TextView的宽、高，单位dp
    private int leftWidth = 80;
    private int leftHeight = 60;
    //分割线的高度，单位dp
    private int lineHeight = 1;
    private OnItemClickListener onItemClickListener;

    public HourView(Context context) {
        super(context, null);
    }

    public HourView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        startTimeComparator = new StartTimeComparator();

        View view = View.inflate(context, R.layout.hour_view, this);
        contentLayout = (AbsoluteLayout) view.findViewById(R.id.contentLayout);
        leftMargin = DimenUtil.dp2px(context, leftWidth);
        contentWidth = DimenUtil.getScreenWidth(context) - leftMargin;
        contentHeight = DimenUtil.dp2px(context, leftHeight * 24 + 23);
//        Log.d(TAG, "leftMargin = " + leftMargin + " & contentLayout :contentWidth = " + contentWidth + ", contentHeight = " + contentHeight);
    }


    private void sortList(List<Conference> list) {
        if (list != null) {
            //当list里面只有一个元素时直接画出来
            if (list.size() == 1) {
                Log.d(TAG, "list.size() == 1 list = " + list.toString());
                Conference conference = list.get(0);
                addView(conference, 0, 0);
                list = null;
            }
            if (list != null && list.size() > 1) {
                Log.d(TAG, "list.size() > 1");
                for (int i = 0; i < list.size() - 1; i++) {
                    int j = 0;
                    while (list.get(j).getEndTime() <= list.get(i + 1).getStartTime()) {
                        j++;
                        if (j > i) {
                            List<Conference> subList = list.subList(i + 1, list.size());
                            Log.d(TAG, "sortList: subList = " + subList.toString());
                            //add view
                            for (int k = 0; k <= i; k++) {
                                Conference conference = list.get(k);
                                addView(conference, i, k);
                            }
                            sortList(subList);
                            return;
                        }
                    }
                }
            }

            for (int i = 0; list != null && i < list.size(); i++) {
                Conference conference = list.get(i);
                addView(conference, list.size() - 1, i);
            }
        }
    }

    /**
     * 添加TextView的方法
     */
    private void addView(Conference conference, int i, int k) {
        float startTime = conference.getStartTime();
        float endTime = conference.getEndTime();
        int width = contentWidth / (i + 1);
        int height = DimenUtil.dp2px(context, (int) ((endTime - startTime) * (leftHeight + lineHeight)));
        int x = width * k;
        int y = DimenUtil.dp2px(context, (int) (startTime * (leftHeight + lineHeight)));
        contentLayout.addView(getView(conference), new AbsoluteLayout.LayoutParams(width, height, x, y));
    }


    @NonNull
    private Button getView(final Conference conference) {
        Button button = new Button(context);
        button.setText(conference.getDesc());
        button.setTextColor(Color.WHITE);
        button.setGravity(Gravity.CENTER);
        button.setAllCaps(false);
        button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = list.indexOf(conference);
                onItemClickListener.onItemClick(position, conference);
            }
        });
        //取消硬件加速，否则文本不能正常显示
        button.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        return button;
    }


    /**
     * 设置数据源
     *
     * @param list
     */
    public void setDataList(List<Conference> list) {
        this.list = list;
        Collections.sort(list, startTimeComparator);
        Log.d(TAG, "sort list = " + list.toString());
        sortList(list);
    }

    /**
     * 添加元素
     *
     * @param conference
     */
    public void add(Conference conference) {
        if (list.add(conference)) {
            notifyDataSetChanged(list);
        }
    }

    /**
     * 删除元素
     *
     * @param conference
     */
    public void remove(Conference conference) {
        if (list.contains(conference) && list.remove(conference)) {
            notifyDataSetChanged(list);
        }
    }

    /**
     * 更新元素
     *
     * @param conference
     */
    public void update(Conference conference) {
        for (int i = 0; i < list.size(); i++) {
            Conference c = list.get(i);
            if (c.getId() == conference.getId()) {
                list.remove(c);
                list.add(conference);
                notifyDataSetChanged(list);
            }
        }
    }

    /**
     * 获取数据源
     */
    public List<Conference> getDataList() {
        return list;
    }

    /**
     * 当数据源改变时传入list集合重新绘制所有元素
     */
    public void notifyDataSetChanged(List<Conference> list) {
        contentLayout.removeAllViews();
        setDataList(list);
    }

    /**
     * 设置每个元素的点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
