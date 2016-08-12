package com.leohan.customview.monthview;

import android.content.Context;
import android.util.AttributeSet;
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

    private Context context;
    private NoTimeHourView leftView;
    private NoTimeHourView centerView;
    private NoTimeHourView rightView;

    public ComboHourView(Context context) {
        super(context, null);
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
     * 设置数据源
     */
    public void setDataList(List<Conference> list1, List<Conference> list2, List<Conference> list3) {
        leftView.setDataList(list1);
        centerView.setDataList(list2);
        rightView.setDataList(list3);
    }

    /**
     * 重新设置数据源
     */
    public void resetDataList(List<Conference> list1, List<Conference> list2, List<Conference> list3) {
        leftView.resetDataList(list1);
        centerView.resetDataList(list2);
        rightView.resetDataList(list3);
    }

}
