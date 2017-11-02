package com.android.project.nnfriends_;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initFont();
    }

    private void initFont() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "gozik.ttf");

        TextView textView1 = (TextView) findViewById(R.id.radio1);
        TextView textView2 = (TextView) findViewById(R.id.radio2);
        TextView textView3 = (TextView) findViewById(R.id.radio3);
        TextView textView4 = (TextView) findViewById(R.id.btnOCR);
        TextView textView5 = (TextView) findViewById(R.id.btnJoin);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);
        textView4.setTypeface(typeface);
        textView5.setTypeface(typeface);
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
