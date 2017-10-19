package com.android.project.nnfriends_;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class WDiaryActivity extends AppCompatActivity {
    static TextView tv_year,tv_day,tv_month;

    //TTS 부분
    boolean ttsReady =false;
    public static int REQ_SPEAK_CODE = 100;
    TextToSpeech tts;
    TextView tv_quest;
    TextView tv_answer;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wdiary);

        init();
    }

    private void init() {

        tv_year = (TextView)findViewById(R.id.year);
        tv_month = (TextView)findViewById(R.id.month);
        tv_day = (TextView)findViewById(R.id.day);

        Intent int_date = getIntent();


        tv_year.setText(int_date.getIntExtra("year", 0)+"");
        tv_month.setText(int_date.getIntExtra("month", 0)+"/");
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
        super.onDestroy();
        tts.shutdown();
    }

    private void initQuest() {

            if(ttsReady){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //질문 읽어주는 부분
                    tts.speak(tv_quest.getText().toString(), TextToSpeech.QUEUE_ADD, null, null);
                    while(true){
                        if(!tts.isSpeaking()){
                            //대답 받는 부분
                            Intent intent = new Intent(
                                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH
                            );
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "대답해");
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
        }
    }

    public void questClick(View view) {
        switch(view.getId()) {
            case R.id.quest1:
                tv_quest = (TextView)findViewById(R.id.quest1);
                tv_answer = (TextView)findViewById(R.id.ans1);
                break;
            case R.id.quest2:
                tv_quest = (TextView)findViewById(R.id.quest2);
                tv_answer = (TextView)findViewById(R.id.ans2);
                break;
            case R.id.quest3:
                tv_quest = (TextView)findViewById(R.id.quest3);
                tv_answer = (TextView)findViewById(R.id.ans3);
                break;
            case R.id.quest4:
                tv_quest = (TextView)findViewById(R.id.quest4);
                tv_answer = (TextView)findViewById(R.id.ans4);
                break;
            case R.id.quest5:
                tv_quest = (TextView)findViewById(R.id.quest5);
                tv_answer = (TextView)findViewById(R.id.ans5);
                break;
        }

        initQuest();
    }

    public void writeClick(View view) {
        //DB에 저장작업
        // 액티비티 끄기
        finish();
    }

}