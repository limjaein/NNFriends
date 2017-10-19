package com.android.project.nnfriends_;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.github.ajalt.reprint.core.AuthenticationFailureReason;
import com.github.ajalt.reprint.core.AuthenticationListener;
import com.github.ajalt.reprint.core.Reprint;

public class LoginActivity extends AppCompatActivity {

    boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
            Log.d("ReprintLog", "4");
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public boolean checkDeviceSpec() {
        boolean finderprintFlag = Reprint.isHardwarePresent();
        boolean hasRegisteredFlag = Reprint.hasFingerprintRegistered();
        Log.d("ReprintLog", finderprintFlag+"");
        Log.d("ReprintLog", hasRegisteredFlag+"");

        return (finderprintFlag && hasRegisteredFlag);
    }
}
