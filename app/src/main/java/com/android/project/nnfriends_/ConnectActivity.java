package com.android.project.nnfriends_;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.project.nnfriends_.Classes.customDialog;

public class ConnectActivity extends AppCompatActivity {
    Typeface typeface;
    customDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        typeface = Typeface.createFromAsset(getAssets(), "gozik.ttf");

        matchSetting(); // 매칭 다이얼로그
        matchCheck(); // 화면 구성
        initFont();
    }

    private void matchSetting() { // 매칭 안 되었을 때만 뜨는 다이얼로그
        //if문 처리 할것!
        dialog = new customDialog(this);
        TextView tv1 = (TextView) dialog.findViewById(R.id.selectAge);
        TextView tv2 = (TextView) dialog.findViewById(R.id.selectgender);
        RadioButton tv3 = (RadioButton) dialog.findViewById(R.id.age1);
        RadioButton tv4 = (RadioButton) dialog.findViewById(R.id.age2);
        RadioButton tv5 = (RadioButton) dialog.findViewById(R.id.age3);
        RadioButton tv6 = (RadioButton) dialog.findViewById(R.id.age4);
        RadioButton tv7 = (RadioButton) dialog.findViewById(R.id.gen1);
        RadioButton tv8 = (RadioButton) dialog.findViewById(R.id.gen2);
        RadioButton tv9 = (RadioButton) dialog.findViewById(R.id.age5);
        RadioButton tv10 = (RadioButton) dialog.findViewById(R.id.gen3);
        tv1.setTypeface(typeface);
        tv2.setTypeface(typeface);
        tv3.setTypeface(typeface);
        tv4.setTypeface(typeface);
        tv5.setTypeface(typeface);
        tv6.setTypeface(typeface);
        tv7.setTypeface(typeface);
        tv8.setTypeface(typeface);
        tv9.setTypeface(typeface);
        tv10.setTypeface(typeface);

        dialog.show();
    }

    private void initFont() {


        TextView textView1 = (TextView) findViewById(R.id.connect_tv1);
        TextView textView2 = (TextView) findViewById(R.id.connect_tv2);
        TextView textView3 = (TextView) findViewById(R.id.connect_tv3);
        TextView textView4 = (TextView) findViewById(R.id.connect_tv4);
        TextView textView5 = (TextView) findViewById(R.id.connect_tv5);
        TextView textView6 = (TextView) findViewById(R.id.connect_tv6);
        TextView textView7 = (TextView) findViewById(R.id.connect_tv7);
        TextView textView8 = (TextView) findViewById(R.id.connect_tv8);
        TextView textView9 = (TextView) findViewById(R.id.connect_tv9);
        TextView textView10 = (TextView) findViewById(R.id.address1);
        TextView textView11 = (TextView) findViewById(R.id.address2);
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


    }

    public void matchCheck() {
    /*매칭 여부 확인하고, 화면 2개로 다르게 해야함*/
//        if(true){
        setContentView(R.layout.activity_connect);
//        }else{
//            setContentView(R.layout.activity_notconnect);
//        }

    }

    public void matchBtnClick(View view) {
        RadioGroup radioAge = (RadioGroup) dialog.findViewById(R.id.age_select);
        RadioGroup radioGender = (RadioGroup) dialog.findViewById(R.id.gender_select);

        radioAge.setOnCheckedChangeListener(cclistener);
        radioGender.setOnCheckedChangeListener(cclistener);

        // 매칭 세팅 저장
        dialog.dismiss();
    }

    RadioGroup.OnCheckedChangeListener cclistener = new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if (radioGroup.getId() == R.id.age_select) {
                switch (i) {
                    case R.id.age1:
                        break;
                    case R.id.age2:
                        break;
                    case R.id.age3:
                        break;
                    case R.id.age4:
                        break;
                    case R.id.age5:
                        break;
                }
            } else if (radioGroup.getId() == R.id.gender_select) {
                switch (i) {
                    case R.id.gen1:
                        break;
                    case R.id.gen2:
                        break;
                    case R.id.gen3:
                        break;
                }
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        Intent reintent = new Intent(this, PinActivity.class);
        startActivity(reintent);
    }
}
