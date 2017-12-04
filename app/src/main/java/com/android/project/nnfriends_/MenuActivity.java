package com.android.project.nnfriends_;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        initFont();

    // Example of a call to a native method
        Toast.makeText(this, stringFromJNI(), Toast.LENGTH_SHORT).show();
    }

    private void initFont() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "gozik.ttf");

        TextView textView1 = (TextView) findViewById(R.id.menu_tv1);
        TextView textView2 = (TextView) findViewById(R.id.menu_tv2);
        TextView textView3 = (TextView) findViewById(R.id.menu_tv3);
        TextView textView4 = (TextView) findViewById(R.id.menu_tv4);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);
        textView4.setTypeface(typeface);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public void menuClick(View view) {
        Intent intent;
        switch(view.getId()){
            case R.id.connectBtn:
                intent = new Intent(this, ConnectActivity.class);
                startActivity(intent);
                break;
            case R.id.diaryBtn:
                intent = new Intent(this, RWSelectActivity.class);
                startActivity(intent);
                break;
            case R.id.groupBtn:
                intent = new Intent(this, GroupActivity.class);
                startActivity(intent);
                break;
            case R.id.friendBtn:
                intent = new Intent(this, FriendActivity.class);
                startActivity(intent);
                break;
        }
    }
}
