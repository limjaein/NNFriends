package com.android.project.nnfriends_;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.project.nnfriends_.Classes.PreferenceManager;
import com.android.project.nnfriends_.Classes.User;
import com.github.ajalt.reprint.core.AuthenticationFailureReason;
import com.github.ajalt.reprint.core.AuthenticationListener;
import com.github.ajalt.reprint.core.Reprint;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    boolean running;
    EditText editID, editPIN;
    String autoId, autoPin;

    DatabaseReference table;

    PreferenceManager pref;
    //static final String KEY_AUTO_LOGIN = "autoLogin";
    static final String KEY_USER_ID = "userID";
    static final String KEY_USER_NAME = "userName";
    static final String KEY_USER_PIN = "userPIN";
    static final String KEY_USER_MATNUM = "matchNum";

    static Typeface typeface;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initAutoLogin();
        initFont();
        init();

        Reprint.initialize(this);

        if (checkDeviceSpec()) {

            final LayoutInflater inflater = getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.dialog_login, null);
            final ImageView check = (ImageView) dialogView.findViewById(R.id.imgFingerprint);

            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
            alert.setView(dialogView);
            alert.setPositiveButton("cancel", null);
            alert.setCancelable(false);
            final AlertDialog dialog = alert.create();
            dialog.show();

            Reprint.authenticate(new AuthenticationListener() {
                @Override
                public void onSuccess(int moduleTag) {
                    check.setImageResource(R.drawable.fingerprint_check);
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(AuthenticationFailureReason failureReason, boolean fatal, CharSequence errorMessage, int moduleTag, int errorCode) {
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
    public void initAutoLogin(){
        SharedPreferences autopref = getSharedPreferences("autopref", Activity.MODE_PRIVATE);

        PreferenceManager pref = new PreferenceManager();
        String Id = pref.getStringPref(LoginActivity.this, KEY_USER_ID);
        String Pin = pref.getStringPref(LoginActivity.this, KEY_USER_PIN);

        autoId = autopref.getString("autoId",null);
        autoPin = autopref.getString("autoPin", null);

        if(autoId !=null && autoPin != null) {
            if(autoId.equals(Id) && autoPin.equals(Pin)) {
                Toast.makeText(LoginActivity.this, autoId +" 님 자동로그인 입니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, PinActivity.class);
                startActivity(intent);

                Intent intent2 = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent2);
            }
        }
    }
    public void init() {
        editID = (EditText) findViewById(R.id.editLoginID);
        editPIN = (EditText) findViewById(R.id.editLoginPIN);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();

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
                loginCheck();
                break;
        }
    }
    public void loginCheck() {
        final String id = editID.getText().toString();
        final String pw = editPIN.getText().toString();
        if (id.length() <= 0 || pw.length() <= 0)
            return;

        table = FirebaseDatabase.getInstance().getReference("NNfriendsDB/UserDB");
        Query query = table.orderByKey().equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    if (user.getuPW().equals(pw)) {
                        Toast.makeText(LoginActivity.this, "로그인 성공!", Toast.LENGTH_LONG).show();
                        pref = new PreferenceManager();
                        SharedPreferences autopref = getSharedPreferences("autopref", Activity.MODE_PRIVATE);

                        SharedPreferences.Editor autoLogin = autopref.edit();
                        autoLogin.putString("autoId", id);
                        autoLogin.putString("autoPin", pw);

                        autoLogin.commit(); // 저장
                        pref.saveStringPref(LoginActivity.this, KEY_USER_ID, id);
                        pref.saveStringPref(LoginActivity.this, KEY_USER_NAME, user.getName());
                        pref.saveStringPref(LoginActivity.this, KEY_USER_PIN, user.getuPW());
                        pref.saveIntPref(LoginActivity.this, KEY_USER_MATNUM, user.getMatchNum());
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    } else {
                        //Log.d("check", user.getUserID());
                        //Log.d("check", user.getUserPW());
                        Toast.makeText(LoginActivity.this, "비밀번호가 틀립니다", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Toast.makeText(LoginActivity.this, "존재하지 않는 아이디입니다", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
