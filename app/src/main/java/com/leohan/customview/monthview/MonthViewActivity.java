package com.leohan.customview.monthview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.leohan.customview.R;

public class MonthViewActivity extends AppCompatActivity {

    private MonthView monthView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_view);
        monthView = (MonthView) findViewById(R.id.monthView);
    }
}
