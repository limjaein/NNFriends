package com.android.project.nnfriends_;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import static com.android.project.nnfriends_.SignupActivity.KEY_FINGER_FLAG;

public class LoginActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    boolean running;
    EditText editID, editPIN;
    String autoId, autoPin;
    Spinner spinner;
    String interNum;
    String myPhone;

    DatabaseReference table;

    PreferenceManager pref;

    static final String KEY_USER_ID = "userID";
    static final String KEY_USER_PIN = "userPIN";
    static final String KEY_USER_NAME = "userName";
    static final String KEY_USER_MATNUM = "matchNum";
    static final String KEY_FINGER_ID = "fingerID";
    static final String KEY_FINGER_PIN = "fingerPIN";
    static final String KEY_FINGER_NAME = "fingerName";
    static final String KEY_FINGER_MATNUM = "fingerMatchNum";

    static Typeface typeface;

    static final int REQ_PERMISSION = 1000;
    private static String[] permission = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_SMS
    };

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

        testInput();

        Reprint.initialize(this);

        if (checkDeviceSpec() && pref.getBooleanPref(LoginActivity.this, KEY_FINGER_FLAG)) {

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

                    String id = pref.getStringPref(LoginActivity.this, KEY_FINGER_ID);
                    String pin = pref.getStringPref(LoginActivity.this, KEY_FINGER_PIN);
                    String name = pref.getStringPref(LoginActivity.this, KEY_FINGER_NAME);
                    String matchNum = pref.getStringPref(LoginActivity.this, KEY_FINGER_MATNUM);


                    pref.saveStringPref(LoginActivity.this, KEY_USER_ID, id);
                    pref.saveStringPref(LoginActivity.this, KEY_USER_PIN, pin);
                    pref.saveStringPref(LoginActivity.this, KEY_USER_NAME, name);
                    pref.saveStringPref(LoginActivity.this, KEY_USER_MATNUM, matchNum);

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

    private void testInput() {

        table = FirebaseDatabase.getInstance().getReference("NNfriendsDB/UserDB");

        User user1 = new User("+821012345678", "1111", "여육십1", 66, 1, "123456-1234567", 0,
                "강남구 개포동 선우아파트", "서울특별시", "강남구", "개포동", "");
        User user2 = new User("+821012345679", "1111", "남육십1", 69, 0, "123456-1234567", 0,
                "강남구 개포동 선우아파트", "서울특별시", "강남구", "개포동", "");
        User user3 = new User("+821012345674", "1111", "여칠십1", 70, 1, "123456-1234567", 0,
                "강남구 개포동 선우아파트", "서울특별시", "강남구", "개포동", "");
        User user4 = new User("+821012345672", "1111", "여육십2", 68, 1, "123456-1234567", 0,
                "광진구 자양동 선우아파트", "서울특별시", "광진구", "자양동", "");
        User user5 = new User("+821012345671", "1111", "여육십3", 65, 1, "123456-1234567", 0,
                "강남구 개포동 선우아파트", "서울특별시", "강남구", "개포동", "");
        User user6 = new User("+821012345622", "1111", "여칠십2", 78, 1, "123456-1234567", 0,
                "강남구 개포동 선우아파트", "서울특별시", "강남구", "개포동", "");
        User user7 = new User("+821012345654", "1111", "남칠십1", 77, 0, "123456-1234567", 0,
                "강남구 개포동 선우아파트", "서울특별시", "강남구", "개포동", "");
        User user8 = new User("+821012345999", "1111", "남팔십1", 82, 0, "123456-1234567", 0,
                "강남구 개포동 선우아파트", "서울특별시", "강남구", "개포동", "");
        User user9 = new User("+821012345123", "1111", "여육십4", 66, 1, "123456-1234567", 0,
                "강남구 개포동 선우아파트", "서울특별시", "강남구", "개포동", "");
        User user10 = new User("+821012344321", "1111", "남칠십2", 74, 0, "123456-1234567", 0,
                "강남구 개포동 선우아파트", "서울특별시", "강남구", "개포동", "");


        table.child(user1.getuID()).setValue(user1);
        table.child(user2.getuID()).setValue(user2);
        table.child(user3.getuID()).setValue(user3);
        table.child(user4.getuID()).setValue(user4);
        table.child(user5.getuID()).setValue(user5);
        table.child(user6.getuID()).setValue(user6);
        table.child(user7.getuID()).setValue(user7);
        table.child(user8.getuID()).setValue(user8);
        table.child(user9.getuID()).setValue(user9);
        table.child(user10.getuID()).setValue(user10);

    }

    public void initAutoLogin() {
        Log.d("checkk", "initauto");
        SharedPreferences autopref = getSharedPreferences("autopref", Activity.MODE_PRIVATE);

        PreferenceManager pref = new PreferenceManager();
        String Id = pref.getStringPref(LoginActivity.this, KEY_USER_ID);
        String Pin = pref.getStringPref(LoginActivity.this, KEY_USER_PIN);


        autoId = autopref.getString("autoId", null);
        autoPin = autopref.getString("autoPin", null);

        if (autoId != null && autoPin != null) {
            if (autoId.equals(Id) && autoPin.equals(Pin)) {

                pref.saveStringPref(LoginActivity.this, KEY_USER_ID, Id);
                pref.saveStringPref(LoginActivity.this, KEY_USER_PIN, Pin);

                Toast.makeText(LoginActivity.this, autoId + " 님 자동로그인 입니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, PinActivity.class);
                startActivity(intent);

                Intent intent2 = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent2);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_PERMISSION) {
            if (grantResults.length > 0) {
                if (checkPermission(permission)) {

                } else {
                    askPermission(permission, REQ_PERMISSION);
                }
            }
        }
    }

    private boolean checkPermission(String[] requestPermission) {
        boolean[] requestResult = new boolean[requestPermission.length];
        for (int i = 0; i < requestResult.length; i++) {
            requestResult[i] = (ContextCompat.checkSelfPermission(this, requestPermission[i]) == PackageManager.PERMISSION_GRANTED);
            if (!requestResult[i]) {
                return false;
            }
        }
        return true;
    }

    private void askPermission(String[] requestPermission, int requestCode) {
        ActivityCompat.requestPermissions(
                this,
                requestPermission,
                requestCode
        );
    }

    public void init() {
        Log.d("checkk", "init");
        if (!checkPermission(permission)) {
            askPermission(permission, REQ_PERMISSION);
            return;
        }
        final TelephonyManager mgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (mgr != null) {
            myPhone = mgr.getLine1Number();
        } else {
            myPhone = "";
        }

        editID = (EditText) findViewById(R.id.editLoginID);
        editID.setText(myPhone);
        editPIN = (EditText) findViewById(R.id.editLoginPIN);
        spinner = (Spinner) findViewById(R.id.phoneSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // kor
                        interNum = "+82";
                        break;
                    case 1: // nld
                        interNum = "+31";
                        break;
                }
                if (myPhone != null) {
                    myPhone = interNum + myPhone.substring(myPhone.length()-10,myPhone.length());
                    editID.setText(myPhone);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();

    }
    private void initFont() {

        typeface = Typeface.createFromAsset(getAssets(), "gozik.ttf");

        TextView textView1 = (TextView) findViewById(R.id.btnLogin);
        TextView textView2 = (TextView) findViewById(R.id.btnSignup);
        TextView textView3 = (TextView) findViewById(R.id.editLoginID);
        TextView textView4 = (TextView) findViewById(R.id.editLoginPIN);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);
        textView4.setTypeface(typeface);
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
                //finish();
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
                        pref.saveStringPref(LoginActivity.this, KEY_USER_MATNUM, user.getMatchNum());
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
