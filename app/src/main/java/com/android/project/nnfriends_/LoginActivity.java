package com.android.project.nnfriends_;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ajalt.reprint.core.AuthenticationFailureReason;
import com.github.ajalt.reprint.core.AuthenticationListener;
import com.github.ajalt.reprint.core.Reprint;

public class LoginActivity extends AppCompatActivity {

    boolean running;

    static Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initFont();

        Reprint.initialize(this);

        if (checkDeviceSpec()) {
            Log.d("ReprintLog", "1");
            Reprint.authenticate(new AuthenticationListener() {
                @Override
                public void onSuccess(int moduleTag) {
                    Log.d("ReprintLog", "2");
                    Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(AuthenticationFailureReason failureReason, boolean fatal, CharSequence errorMessage, int moduleTag, int errorCode) {
                    Log.d("ReprintLog", "3");
                    Toast.makeText(LoginActivity.this, "failure", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // 지문기능이 없는 경우
//            Log.d("ReprintLog", "4");
//            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
//            startActivity(intent);
//            finish();
        }
    }
    private void initFont() {

        typeface = Typeface.createFromAsset(getAssets(), "gozik.ttf");

        TextView textView1 = (TextView) findViewById(R.id.btnLogin);
        TextView textView2 = (TextView) findViewById(R.id.btnSignup);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
    }

    public boolean checkDeviceSpec() {
        boolean finderprintFlag = Reprint.isHardwarePresent();
        boolean hasRegisteredFlag = Reprint.hasFingerprintRegistered();
        Log.d("ReprintLog", finderprintFlag+"");
        Log.d("ReprintLog", hasRegisteredFlag+"");

        return (finderprintFlag && hasRegisteredFlag);
    }

    public void btnClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnSignup:
                intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnLogin:
                intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
