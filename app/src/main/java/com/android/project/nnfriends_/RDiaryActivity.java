package com.android.project.nnfriends_;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.android.project.nnfriends_.Classes.Diary;
import com.android.project.nnfriends_.Classes.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.android.project.nnfriends_.LoginActivity.KEY_USER_ID;
import static com.android.project.nnfriends_.LoginActivity.KEY_USER_MATNUM;

public class RDiaryActivity extends MyActivity {
    String year, month, day;
    TextView tv_year, tv_month, tv_day, tv_writer, tv_writeTime;
    TextView tv_ans1, tv_ans2, tv_ans3, tv_ans4, tv_ans5;

    DatabaseReference table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdiary);

        initFont();
        init();
    }

    private void initFont() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "gozik.ttf");

        tv_year = (TextView)findViewById(R.id.r_year);
        tv_month = (TextView)findViewById(R.id.r_month);
        tv_day = (TextView)findViewById(R.id.r_day);
        tv_writer = (TextView) findViewById(R.id.writer);
        tv_writeTime = (TextView) findViewById(R.id.writeTime);
        tv_ans1 = (TextView) findViewById(R.id.ans1r);
        tv_ans2 = (TextView) findViewById(R.id.ans2r);
        tv_ans3 = (TextView) findViewById(R.id.ans3r);
        tv_ans4 = (TextView) findViewById(R.id.ans4r);
        tv_ans5 = (TextView) findViewById(R.id.ans5r);
        TextView textView4 = (TextView) findViewById(R.id.quest1);
        TextView textView5 = (TextView) findViewById(R.id.quest2);
        TextView textView6 = (TextView) findViewById(R.id.quest3);
        TextView textView7 = (TextView) findViewById(R.id.quest4);
        TextView textView8 = (TextView) findViewById(R.id.quest5);

        tv_year.setTypeface(typeface);
        tv_month.setTypeface(typeface);
        tv_day.setTypeface(typeface);
        tv_writer.setTypeface(typeface);
        tv_writeTime.setTypeface(typeface);
        tv_ans1.setTypeface(typeface);
        tv_ans2.setTypeface(typeface);
        tv_ans3.setTypeface(typeface);
        tv_ans4.setTypeface(typeface);
        tv_ans5.setTypeface(typeface);
        textView4.setTypeface(typeface);
        textView5.setTypeface(typeface);
        textView6.setTypeface(typeface);
        textView7.setTypeface(typeface);
        textView8.setTypeface(typeface);
    }

    private void init() {

        Intent int_date = getIntent();

        year = int_date.getIntExtra("year", 0)+"";
        month = int_date.getIntExtra("month", 0)+"";
        day = int_date.getIntExtra("day", 0)+"";

        tv_year.setText(year+"");
        tv_month.setText(month+" / ");
        tv_day.setText(day+"");

        table = FirebaseDatabase.getInstance().getReference("NNfriendsDB/DiaryDB");
        PreferenceManager pref = new PreferenceManager();
        final String matchNum = pref.getStringPref(RDiaryActivity.this, KEY_USER_MATNUM);
        final String id = pref.getStringPref(RDiaryActivity.this, KEY_USER_ID);
        String key = matchNum + "_" + year + month + day;

        table.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Diary diary = data.getValue(Diary.class);
                    if (diary.getvID().equals(id)&&diary.getvYear().equals(year)&&diary.getvMonth().equals(month)&&diary.getvDay().equals(day)) {
                        tv_writer.setText(diary.getvName());
                        tv_writeTime.setText(diary.getwTime());
                        tv_ans1.setText(diary.getAnswer1());
                        tv_ans2.setText(diary.getAnswer2());
                        tv_ans3.setText(diary.getAnswer3());
                        tv_ans4.setText(diary.getAnswer4());
                        tv_ans5.setText(diary.getAnswer5());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
