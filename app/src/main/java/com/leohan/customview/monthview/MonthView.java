package com.leohan.customview.monthview;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.leohan.customview.R;
import com.leohan.customview.common.DimenUtil;
import com.leohan.customview.common.MultiConference;

import java.util.ArrayList;
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
        void onItemClick(int position, MultiConference conference);
    }

    private static final String TAG = "MonthView";
    private List<MultiConference> list;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private ViewPager viewPager;
    private List<ComboHourView> comboHourViews;
    private MultiConference conference;
    private ComboHourView comboHourView;
    //左侧时间列表TextView的宽度，单位px
    private int leftMargin;
    //左边TextView的宽、高，单位dp
    private int leftWidth = 50, leftHeight = 80;
    private int lineHeight = 1;
    //悬浮窗
    private LinearLayout linearLayout;
    private WindowManager windowManager;
    private WindowManager.LayoutParams params;
    private Button floatButton;
    private View view;
    private LinearLayout leftLayout;


    public MonthView(Context context) {
        super(context, null);
    }

    public MonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        comboHourViews = new ArrayList<>();
        init();
    }

    private void init() {
        leftMargin = DimenUtil.dp2px(context, leftWidth);
        float height = DimenUtil.dp2px(context, 80);
        Log.d(TAG, "init: height = " + height);

        final View view = View.inflate(context, R.layout.month_view, this);
        leftLayout = (LinearLayout) view.findViewById(R.id.leftLayout);
        leftLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (event.getX() < leftMargin) {
                            if (linearLayout != null) {
                                windowManager.removeView(linearLayout);
                            }
                            createFloatView((int) event.getY());
                            Log.d(TAG, "onTouchEvent ACTION_DOWN : event.getY() = " + event.getY());
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //update view
                        params.y = (int) event.getY();
                        windowManager.updateViewLayout(linearLayout, params);
                        //Log.d(TAG, "onTouchEvent: ACTION_MOVE : event.getY() = " + event.getY());
                        //计算时间
                        final int i = DimenUtil.px2dp(context, (int) event.getY());
                        final int j = i / (leftHeight + lineHeight);
                        float t = DimenUtil.px2dp(context, (int) event.getY() + 1) / (leftHeight + lineHeight) + 1 / 8;
                        Log.d(TAG, "createFloatView: i,j = " + i + "," + j);
                        //button setText
                        break;
                    case MotionEvent.ACTION_UP:
                        //remove view
                        windowManager.removeView(linearLayout);
                }
                return false;
            }
        });
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                if (comboHourViews.get(position % comboHourViews.size()).getParent() != null) {
                    ((ViewPager) comboHourViews.get(position % comboHourViews.size())
                            .getParent()).removeView(comboHourViews.get(position
                            % comboHourViews.size()));
                }
                container.addView(
                        comboHourViews.get(position % comboHourViews.size()), 0);
                return comboHourViews.get(position % comboHourViews.size());
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(comboHourViews.get(position
                        % comboHourViews.size()));
            }

        });
        viewPager.setCurrentItem(499);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: position = " + position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged: state " + state);
            }
        });

        addTestData();
        comboHourView = new ComboHourView(context);
        comboHourView.resetDataList(list, list, list);
        comboHourViews.add(comboHourView);
        comboHourView = new ComboHourView(context);
        comboHourView.resetDataList(list, list, list);
        comboHourViews.add(comboHourView);
        comboHourView = new ComboHourView(context);
        comboHourView.resetDataList(list, list, list);
        comboHourViews.add(comboHourView);
    }


    /**
     * 添加测试数据
     */
    private List addTestData() {
        list = new ArrayList<>();
        conference = new MultiConference(6, "t6", 0, 1,MultiConference.CONF_CLOUD);
        list.add(conference);
        return list;
    }

    private void createFloatView(int y) {
        params = new WindowManager.LayoutParams();
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //设置window type
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        //设置图片格式，效果为背景透明
        params.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //调整悬浮窗显示的停靠位置为左侧置顶
        params.gravity = Gravity.LEFT | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        params.x = 0;
        final int screenHeight = DimenUtil.getScreenHeight(context);
        if (y < screenHeight) {
            params.y = y;
        } else
            while (y > screenHeight) {
                y = y - screenHeight;
            }
        params.y = screenHeight - y;
        //设置悬浮窗口长宽数据
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        linearLayout = (LinearLayout) View.inflate(context, R.layout.time_tip_line, null);
        windowManager.addView(linearLayout, params);
        linearLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        Button button = (Button) linearLayout.findViewById(R.id.button);


    }
}
