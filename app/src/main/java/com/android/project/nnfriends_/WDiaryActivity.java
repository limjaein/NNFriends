package com.android.project.nnfriends_;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.project.nnfriends_.Classes.Diary;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WDiaryActivity extends AppCompatActivity {
    Intent int_date;
    static TextView tv_year,tv_day,tv_month;

    //TTS 부분
    boolean ttsReady =false;
    public static int REQ_SPEAK_CODE = 100;
    TextToSpeech tts;
    TextView tv_quest;
    TextView tv_answer;

    String ans1, ans2, ans3, ans4, ans5;
    int ansNum;

    DatabaseReference table;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wdiary);

        initFont();
        init();
    }

    private void initFont() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "gozik.ttf");

        TextView textView1 = (TextView) findViewById(R.id.year);
        TextView textView2 = (TextView) findViewById(R.id.day);
        TextView textView3 = (TextView) findViewById(R.id.month);
        TextView textView4 = (TextView) findViewById(R.id.quest1);
        TextView textView5 = (TextView) findViewById(R.id.quest2);
        TextView textView6 = (TextView) findViewById(R.id.quest3);
        TextView textView7 = (TextView) findViewById(R.id.quest4);
        TextView textView8 = (TextView) findViewById(R.id.quest5);
        TextView textView9 = (TextView) findViewById(R.id.ans4);
        TextView textView10 = (TextView) findViewById(R.id.ans1);
        TextView textView11 = (TextView) findViewById(R.id.ans2);
        TextView textView12 = (TextView) findViewById(R.id.ans3);
        TextView textView13 = (TextView) findViewById(R.id.ans5);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);
        textView4.setTypeface(typeface);
        textView5.setTypeface(typeface);
        textView6.setTypeface(typeface);
        textView7.setTypeface(typeface);
        textView8.setTypeface(typeface);
        textView9.setTypeface(typeface);
        textView10.setTypeface(typeface);
        textView11.setTypeface(typeface);
        textView12.setTypeface(typeface);
        textView13.setTypeface(typeface);
    }

    private void init() {

        tv_year = (TextView)findViewById(R.id.year);
        tv_month = (TextView)findViewById(R.id.month);
        tv_day = (TextView)findViewById(R.id.day);

        int_date = getIntent();


        tv_year.setText(int_date.getIntExtra("year", 0)+"");
        tv_month.setText(int_date.getIntExtra("month", 0)+" / ");
        tv_day.setText(int_date.getIntExtra("day", 0)+"");

        //TTS부분
        tts = new TextToSpeech(
                this,
                new TextToSpeech.OnInitListener(){
                    @Override
                    public void onInit(int status) {
                                ttsReady = true;
                    }
                }
        );

    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    private void initQuest() {

            if(ttsReady){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //질문 읽어주는 부분
                    tts.setLanguage(Locale.ENGLISH);
                    tts.speak(tv_quest.getText().toString(), TextToSpeech.QUEUE_ADD, null, null);
                    while(true){
                        if(!tts.isSpeaking()){
                            //대답 받는 부분
                            Intent intent = new Intent(
                                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH
                            );
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "대답하세요");
                            startActivityForResult(intent, REQ_SPEAK_CODE);
                            break;
                        }
                    }
                }else
                {
                    tts.speak(tv_quest.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                }
            }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_SPEAK_CODE){
            ArrayList<String> list =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if(list == null || list.isEmpty()){
                return;
            }
            tv_answer.setText(list.get(0));
            switch (ansNum) {
                case 1:
                    ans1 = list.get(0);
                    break;
                case 2:
                    ans2 = list.get(0);
                    break;
                case 3:
                    ans3 = list.get(0);
                    break;
                case 4:
                    ans4 = list.get(0);
                    break;
                case 5:
                    ans5 = list.get(0);
                    break;
            }
        }
    }

    public void questClick(View view) {
        switch(view.getId()) {
            case R.id.quest1:
                tv_quest = (TextView)findViewById(R.id.quest1);
                tv_answer = (TextView)findViewById(R.id.ans1);
                ansNum = 1;
                break;
            case R.id.quest2:
                tv_quest = (TextView)findViewById(R.id.quest2);
                tv_answer = (TextView)findViewById(R.id.ans2);
                ansNum = 2;
                break;
            case R.id.quest3:
                tv_quest = (TextView)findViewById(R.id.quest3);
                tv_answer = (TextView)findViewById(R.id.ans3);
                ansNum = 3;
                break;
            case R.id.quest4:
                tv_quest = (TextView)findViewById(R.id.quest4);
                tv_answer = (TextView)findViewById(R.id.ans4);
                ansNum = 4;
                break;
            case R.id.quest5:
                tv_quest = (TextView)findViewById(R.id.quest5);
                tv_answer = (TextView)findViewById(R.id.ans5);
                ansNum = 5;
                break;
        }

        initQuest();
    }

    public void writeClick(View view) {
        //DB에 저장작업
        final String year = String.valueOf(int_date.getIntExtra("year", 0));
        final String month = String.valueOf(int_date.getIntExtra("month", 0));
        final String day = String.valueOf(int_date.getIntExtra("day", 0));

        table = FirebaseDatabase.getInstance().getReference("NNfriendsDB/DiaryDB");
        table.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int matchNum = 0; // 처리해야됨
                String key = String.valueOf(matchNum) + "_" + year + month + day;
                SimpleDateFormat wTime = new SimpleDateFormat("yyyy년 MM월 dd일 a hh:mm"); // 작성시간
                final Date today = new Date();

                DatabaseReference diaryRef = table.child(key);
                Diary diary = new Diary(key, "01027679287", matchNum, year, month, day, wTime.format(today), ans1, ans2, ans3, ans4, ans5);
                diaryRef.setValue(diary);
                Toast.makeText(WDiaryActivity.this, "작성 완료", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // 액티비티 끄기
        finish();
    }

}
