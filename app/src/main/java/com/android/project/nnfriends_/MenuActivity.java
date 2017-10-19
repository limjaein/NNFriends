package com.android.project.nnfriends_;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    // Example of a call to a native method
        Toast.makeText(this, stringFromJNI(), Toast.LENGTH_SHORT).show();
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
                intent = new Intent(this, DiaryActivity.class);
                startActivity(intent);
                break;
            case R.id.groupBtn:
                intent = new Intent(this, GroupActivity.class);
                startActivity(intent);
                break;
            case R.id.friendBtn:
                intent = new Intent();
                startActivity(intent);
                break;
        }
    }
}
