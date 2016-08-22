package com.leohan.customview.hourview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.leohan.customview.common.DimenUtil;
import com.leohan.customview.common.MultiConference;
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
    private List<MultiConference> list = new ArrayList<>();
    private MultiConference conference;
    private WindowManager windowManager;
    private WindowManager.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hour_view);
        hourView = (HourView) findViewById(R.id.hourView);
        //添加测试数据
        addTestData();
        //绑定数据源
        hourView.setDataList(list);

        //hourView的长按事件
        hourView.setOnItemLongClickListener(new HourView.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position, final int downX, final int downY) {
                //1.view透明度变为0.1
                view.setAlpha(0.2f);
                final int width = view.getWidth();
                final int height = view.getHeight();
                //2.显示悬浮窗
                final View v = view;
                ViewGroup viewGroup = (ViewGroup) v.getParent();
                viewGroup.removeAllViews();
                v.setAlpha(1);
                params = new WindowManager.LayoutParams();
                windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                //设置window type
                params.type = WindowManager.LayoutParams.TYPE_PHONE;
                //设置图片格式，效果为背景透明
                params.format = PixelFormat.RGBA_8888;
                //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
                params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                //调整悬浮窗显示的停靠位置为左侧置顶
                params.gravity = Gravity.LEFT | Gravity.TOP;
                // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
                params.x = downX - width / 2;
                params.y = downY - height / 2;
                //设置悬浮窗口长宽数据
                params.width = width;
                params.height = height;
                windowManager.addView(v, params);
            }
        });

    }

    /**
     * 创建悬浮窗
     */
    private void createFloatView(View v, int x, int y) {

    }

    /**
     * 添加测试数据
     */
    private void addTestData() {
        conference = new MultiConference(1, "t1", 2, 7, MultiConference.CONF_CLOUD);
        list.add(conference);
        conference = new MultiConference(2, "T2", 2, 6, MultiConference.CONF_PLACE);
        list.add(conference);
        conference = new MultiConference(3, "t3", 5, 16, MultiConference.CONF_NONE);
        list.add(conference);
        conference = new MultiConference(4, "t4", 0, 1, MultiConference.CONF_CLOUD);
        list.add(conference);
        conference = new MultiConference(11, "t11", 10.5f, 16.8f, MultiConference.CONF_PLACE);
        list.add(conference);
    }
}
