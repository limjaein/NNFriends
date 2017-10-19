package com.android.project.nnfriends_;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class RDiaryActivity extends AppCompatActivity {
    String year, month, day;
    TextView tv_year, tv_month, tv_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdiary);

        init();
    }

    private void init() {

        Intent int_date = getIntent();

        tv_year = (TextView)findViewById(R.id.r_year);
        tv_month = (TextView)findViewById(R.id.r_month);
        tv_day = (TextView)findViewById(R.id.r_day);

        year = int_date.getIntExtra("year", 0)+"";
        month = int_date.getIntExtra("month", 0)+"";
        day = int_date.getIntExtra("day", 0)+"";

        tv_year.setText(year+"");
        tv_month.setText(month+"/");
        tv_day.setText(day+"");

    }
}
