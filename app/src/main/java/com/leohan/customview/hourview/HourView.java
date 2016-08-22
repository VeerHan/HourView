package com.leohan.customview.hourview;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.LinearLayout;

import com.leohan.customview.R;
import com.leohan.customview.common.DimenUtil;
import com.leohan.customview.common.MultiConference;
import com.leohan.customview.common.StartTimeComparator;

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
    public interface OnItemClickListener {
        void onItemClick(int position, MultiConference conference);
    }

    /**
     * 每个元素的长按接口
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position, int downX, int downY);
    }

    private static final String TAG = "HourView";
    //左侧时间列表TextView的宽度，单位px
    private int leftMargin;
    //右侧整块区域，用于添加显示时间方块
    private AbsoluteLayout contentLayout;
    //右侧整块AbsoluteLayout的宽度和高度，单位px
    private int contentWidth, contentHeight;
    private List<MultiConference> list;
    private Context context;
    private StartTimeComparator startTimeComparator;
    //左边TextView的宽、高，单位dp
    private int leftWidth = 80;
    private int leftHeight = 60;
    //分割线的高度，单位dp
    private int lineHeight = 1;
    //会议元素的颜色
    private int color;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private WindowManager windowManager;
    private WindowManager.LayoutParams params;
    private int downX, downY;

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


    private void sortList(List<MultiConference> list) {
        if (list != null) {
            //当list里面只有一个元素时直接画出来
            if (list.size() == 1) {
                Log.d(TAG, "list.size() == 1 list = " + list.toString());
                MultiConference conference = list.get(0);
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
                            List<MultiConference> subList = list.subList(i + 1, list.size());
                            Log.d(TAG, "sortList: subList = " + subList.toString());
                            //add view
                            for (int k = 0; k <= i; k++) {
                                MultiConference conference = list.get(k);
                                addView(conference, i, k);
                            }
                            sortList(subList);
                            return;
                        }
                    }
                }
            }

            for (int i = 0; list != null && i < list.size(); i++) {
                MultiConference conference = list.get(i);
                addView(conference, list.size() - 1, i);
            }
        }
    }

    /**
     * 添加View的方法
     */
    private void addView(MultiConference conference, int i, int k) {
        float startTime = conference.getStartTime();
        float endTime = conference.getEndTime();
        int width = contentWidth / (i + 1);
        int height = DimenUtil.dp2px(context, (int) ((endTime - startTime) * (leftHeight + lineHeight)));
        int x = width * k;
        int y = DimenUtil.dp2px(context, (int) (startTime * (leftHeight + lineHeight)));
        contentLayout.addView(getView(conference), new AbsoluteLayout.LayoutParams(width, height, x, y));
    }


    @NonNull
    private Button getView(final MultiConference conference) {
        Button button = new Button(context);
        button.setAlpha(0.6f);
        button.setText(conference.getDesc());
        button.setTextColor(Color.WHITE);
        button.setGravity(Gravity.CENTER);
        button.setAllCaps(false);

        //判断会议类型
        final int type = conference.getType();
        switch (type) {
            //云会议
            case MultiConference.CONF_CLOUD:
                color = R.color.conf_cloud;
                break;
            //地点会议
            case MultiConference.CONF_PLACE:
                color = R.color.conf_place;
                break;
            //非云会议非地点会议
            case MultiConference.CONF_NONE:
                color = R.color.conf_none;
                break;
        }
        button.setBackgroundColor(getResources().getColor(color));
        button.setAlpha(0.5f);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = list.indexOf(conference);
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, conference);
                }
            }
        });
        button.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = list.indexOf(conference);
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(v, position, downX, downY);
                }
                return false;
            }
        });
        //取消硬件加速，否则文本不能正常显示
        button.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        return button;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                Log.d(TAG, "onInterceptTouchEvent: x,y =" + downX + " " + downY);
                return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 设置数据源
     *
     * @param list
     */
    public void setDataList(List<MultiConference> list) {
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
    public void add(MultiConference conference) {
        if (list.add(conference)) {
            resetDataList(list);
        }
    }

    /**
     * 删除元素
     *
     * @param conference
     */
    public void remove(MultiConference conference) {
        if (list.contains(conference) && list.remove(conference)) {
            resetDataList(list);
        }
    }

    /**
     * 获取数据源
     */
    public List<MultiConference> getDataList() {
        return list;
    }

    /**
     * 当数据源改变时传入list集合重新绘制所有元素
     */
    public void resetDataList(List<MultiConference> list) {
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

    /**
     * 设置每个元素的长按事件
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }


}
