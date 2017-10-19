package com.android.project.nnfriends_;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RWSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rwselect);




    }

    public void diaryClick(View view) {
        Intent Intent_diary;
        Intent intent_date = getIntent();

        if(view.getId()==R.id.write_btn){
            Intent_diary = new Intent(this, WDiaryActivity.class);

            //날짜 intent에 담기
            Intent_diary.putExtra("year", intent_date.getIntExtra("year", 0));
            Intent_diary.putExtra("month", intent_date.getIntExtra("month", 0));
            Intent_diary.putExtra("day", intent_date.getIntExtra("day", 0));

            startActivity(Intent_diary);
        }
        else if(view.getId()==R.id.read_btn){
            Intent_diary = new Intent(this, RDiaryActivity.class);

            //날짜 intent에 담기
            Intent_diary.putExtra("year", intent_date.getIntExtra("year", 0));
            Intent_diary.putExtra("month", intent_date.getIntExtra("month", 0));
            Intent_diary.putExtra("day", intent_date.getIntExtra("day", 0));

            startActivity(Intent_diary);
        }

    }
}
