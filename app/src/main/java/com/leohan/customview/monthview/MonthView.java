package com.leohan.customview.monthview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.LinearLayout;

import com.leohan.customview.R;
import com.leohan.customview.common.Conference;
import com.leohan.customview.common.DimenUtil;
import com.leohan.customview.common.StartTimeComparator;

import java.util.Collections;
import java.util.List;

/**
 * 自定义日视图
 * Created by lei.han on 2016/8/3.
 */

public class MonthView extends LinearLayout {

    /**
     * 每个元素的点击事件接口
     */
    interface OnItemClickListener {
        void onItemClick(int position, Conference conference);
    }

    private static final String TAG = "MonthView";
    //左侧时间列表TextView的宽度，单位px
    private int leftMargin;
    //右侧整块区域，用于添加显示时间方块
    private AbsoluteLayout contentLayout;
    //右侧整块AbsoluteLayout的宽度和高度，单位px
    private List<Conference> list;
    private Context context;
    private StartTimeComparator startTimeComparator;
    //左边TextView的宽、高，单位dp
    private int leftWidth = 80;
    private int leftHeight = 140;
    //分割线的高度，单位dp
    private int lineHeight = 1;
    private OnItemClickListener onItemClickListener;
    private ViewPager viewPager;

    public MonthView(Context context) {
        super(context, null);
    }

    public MonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        startTimeComparator = new StartTimeComparator();
        View view = View.inflate(context, R.layout.month_view, this);

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        leftMargin = DimenUtil.dp2px(context, leftWidth);
//        Log.d(TAG, "leftMargin = " + leftMargin + " & contentLayout :contentWidth = " + contentWidth + ", contentHeight = " + contentHeight);
    }

}
