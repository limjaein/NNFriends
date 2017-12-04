package com.android.project.nnfriends_;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.project.nnfriends_.Classes.PreferenceManager;

import static com.android.project.nnfriends_.LoginActivity.KEY_USER_PIN;
import static com.android.project.nnfriends_.LoginActivity.typeface;

public class PinActivity extends AppCompatActivity {
    TextView password;
    PreferenceManager pref;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        init();
    }

    private void init() {
        password = (TextView)findViewById(R.id.passView);
        password.setTypeface(typeface);
    }

    @Override
    protected void onStart() {
        super.onStart();

        CustomApp app = (CustomApp)getApplication();
        //현재 액티비티 저장
        app.setPreActivity("PinActivity");
    }

    public void passClick(View view) {

        String prepassword = password.getText().toString();

        if(prepassword.length()<=3){
            switch(view.getId()){
                case R.id.num0:
                    password.setText(prepassword+"0");
                    break;
                case R.id.num1:
                    password.setText(prepassword+"1");
                    break;
                case R.id.num2:
                    password.setText(prepassword+"2");
                    break;
                case R.id.num3:
                    password.setText(prepassword+"3");
                    break;
                case R.id.num4:
                    password.setText(prepassword+"4");
                    break;
                case R.id.num5:
                    password.setText(prepassword+"5");
                    break;
                case R.id.num6:
                    password.setText(prepassword+"6");
                    break;
                case R.id.num7:
                    password.setText(prepassword+"7");
                    break;
                case R.id.num8:
                    password.setText(prepassword+"8");
                    break;
                case R.id.num9:
                    password.setText(prepassword+"9");
                    break;
                case R.id.eraseBtn:
                    if(prepassword.length()>0){
                        password.setText(prepassword.substring(0,prepassword.length()-1));
                    }
                    break;
            }
            if(password.length()==4){
                //비번 확인! 틀리면 초기화해주기 맞으면 넘어가기
                String pin = pref.getStringPref(PinActivity.this, KEY_USER_PIN);
                if (password.getText().toString().equals(pin)) {
                    this.finish();
                } else {
                    password.setText("");
                    Toast.makeText(this, "PIN번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
