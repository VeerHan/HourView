package com.leohan.customview.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.leohan.customview.R;
import com.leohan.customview.hourview.HourViewActivity;
import com.leohan.customview.monthview.MonthViewActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnHour, btnMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnHour = (Button) findViewById(R.id.btn_hour);
        btnMonth = (Button) findViewById(R.id.btn_month);
        btnHour.setOnClickListener(this);
        btnMonth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Class clazz = null;
        switch (v.getId()) {
            case R.id.btn_hour:
                clazz = HourViewActivity.class;
                break;
            case R.id.btn_month:
                clazz = MonthViewActivity.class;
                break;
        }
        startActivity(new Intent(MainActivity.this, clazz));
    }
}
