package com.android.project.nnfriends_;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void btnClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnOCR:
                intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btnJoin:
                intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
