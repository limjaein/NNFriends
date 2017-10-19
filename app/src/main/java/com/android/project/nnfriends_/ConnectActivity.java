package com.android.project.nnfriends_;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ConnectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        matchCheck(); // 화면 구성
    }

    public void matchCheck(){
    /*매칭 여부 확인하고, 화면 2개로 다르게 해야함*/
//        if(true){
            setContentView(R.layout.activity_connect);
//        }else{
//            setContentView(R.layout.activity_notconnect);
//        }

    }
}
