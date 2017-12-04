package com.android.project.nnfriends_;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by jaein on 2017-12-03.
 */

public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("NONO FRIEND ^_______^");
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffd5c6b0));


    }
    //액션버튼 메뉴 액션바에 집어 넣기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        //or switch문을 이용하면 될듯 하다.
        if (id == R.id.action_logout) {
            Toast.makeText(this, "로그아웃 클릭", Toast.LENGTH_SHORT).show();

            SharedPreferences auto = getSharedPreferences("autopref", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = auto.edit();

            editor.clear();
            editor.commit();

            //잠금 화면 안뜨게 처리
            CustomApp app = (CustomApp) getApplication();
            app.setPreActivity("PinActivity");
            finish();

            //새로운 앱시작
            Intent i = new Intent(this, LoginActivity.class);
            app.setPreActivity("Activity");
            startActivity(i);

            return true;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        CustomApp app = (CustomApp)getApplication();


        if(app.getIsSetPassword()==true){
            if((app.getPreActivity()).equals("Activity")){
                Intent intent = new Intent(this, PinActivity.class);
                startActivity(intent);
            }
            app.setPreActivity("Activity");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        CustomApp app = (CustomApp)getApplication();
        app.setPreActivity("PinActivity");
    }
}
