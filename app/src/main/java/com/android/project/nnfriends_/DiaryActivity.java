package com.android.project.nnfriends_;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class DiaryActivity extends AppCompatActivity {
    //datepicker 부분
    public static int year;
    public static int day;
    public static int month;
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
        setContentView(R.layout.activity_diary);

        init();
    }

    private void init() {
        //datepicker부분
        tv_year = (TextView)findViewById(R.id.year);
        tv_month = (TextView)findViewById(R.id.month);
        tv_day = (TextView)findViewById(R.id.day);

        DialogFragment dialogfragment = new DatePickerDialogTheme();
        dialogfragment.show(getFragmentManager(), "Theme");

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

    public static class DatePickerDialogTheme extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,this,year,month,day);

            return datepickerdialog;
        }
        public void onResume(){
            super.onResume();
    //        Window window = getDialog().getWindow();
    //        window.setLayout(1200,1500);
    //        window.setGravity(Gravity.CENTER);
        }
        public void onDateSet(DatePicker view, int year, int month, int day){
            tv_year.setText(year+"");
            tv_month.setText("/ "+(month+1)+" ");
            tv_day.setText("/ "+day);

        }
}
}
