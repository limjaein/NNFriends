package com.android.project.nnfriends_;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.project.nnfriends_.Classes.AddressDialog;
import com.android.project.nnfriends_.Classes.PreferenceManager;
import com.android.project.nnfriends_.Classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import static com.android.project.nnfriends_.LoginActivity.KEY_FINGER_ID;
import static com.android.project.nnfriends_.LoginActivity.KEY_FINGER_MATNUM;
import static com.android.project.nnfriends_.LoginActivity.KEY_FINGER_NAME;
import static com.android.project.nnfriends_.LoginActivity.KEY_FINGER_PIN;
import static com.android.project.nnfriends_.LoginActivity.typeface;

public class SignupActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    EditText editID, editPIN, editName, editSSN1, editSSN2, editHelperID;
    Button btnJoin;
    ImageButton btnIDcheck, btnPINcheck, btnAddress;
    RadioGroup stateGroup;
    int state, age, gender;
    boolean checkID, showPIN;
    Spinner spinner;
    String interNum, myPhone, gu, dong, detail;
    TextView txtInterNum,txtInterNum2;

    DatabaseReference table;

    PreferenceManager pref;
    static final String KEY_FINGER_FLAG = "fingerFlag";

    AddressDialog  dialog;
    Typeface typeface;

    static final int REQ_PERMISSION = 1000;
    private static String[] permission = {
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.READ_SMS
    };

    int[] entries = {
            R.array.dong0, R.array.dong1, R.array.dong2, R.array.dong3, R.array.dong4, R.array.dong5, R.array.dong6, R.array.dong7, R.array.dong8, R.array.dong9,
            R.array.dong10, R.array.dong11, R.array.dong12, R.array.dong13, R.array.dong14, R.array.dong15, R.array.dong16, R.array.dong17, R.array.dong18, R.array.dong19,
            R.array.dong20, R.array.dong21, R.array.dong22, R.array.dong23, R.array.dong24, R.array.dong25
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initFont();
        init();
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

    private void init() {

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

        editID = (EditText) findViewById(R.id.editID);
        editPIN = (EditText) findViewById(R.id.editPIN);
        editName = (EditText) findViewById(R.id.editName);
        editSSN1 = (EditText) findViewById(R.id.editSSN1);
        editSSN2 = (EditText) findViewById(R.id.editSSN2);
        editHelperID = (EditText) findViewById(R.id.editHelperID);
        btnIDcheck = (ImageButton) findViewById(R.id.btnIDcheck);
        btnJoin = (Button) findViewById(R.id.btnJoin);
        btnPINcheck = (ImageButton) findViewById(R.id.btnPINcheck);
        btnAddress = (ImageButton) findViewById(R.id.btnAddress);
        stateGroup = (RadioGroup) findViewById(R.id.stateGroup);
        //btnFingerprint = (RadioButton) findViewById(R.id.btnFingerprint);
        checkID = false;
        showPIN = false;
        txtInterNum = (TextView) findViewById(R.id.internum);
        txtInterNum2 = (TextView) findViewById(R.id.internum2);

        interNum = "+82";
        spinner = (Spinner) findViewById(R.id.signupSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                checkID = false;
                btnIDcheck.setImageResource(R.drawable.check_no);

                switch (position) {
                    case 0: // kor
                        interNum = "+82";
                        txtInterNum.setText("+82 0");
                        txtInterNum2.setText("+82 0");
                        break;
                    case 1: // nld
                        interNum = "+31";
                        txtInterNum.setText("+31 0");
                        txtInterNum2.setText("+31 0");
                        break;
                }
                if (myPhone != null) {
                    myPhone = interNum + myPhone.substring(myPhone.length()-10,myPhone.length());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (myPhone != null) {
            editID.setText(myPhone.substring(myPhone.length()-10,myPhone.length()));
            if (myPhone.substring(0, 2).equals("+82")) {
                myPhone = "+82" + myPhone.substring(myPhone.length()-10,myPhone.length());
                txtInterNum.setText("+82 0");
                spinner.setSelection(0);
            } else if (myPhone.substring(0, 2).equals("+31")) {
                myPhone = "+31" + myPhone.substring(myPhone.length()-10,myPhone.length());
                txtInterNum.setText("+31 0");
                spinner.setSelection(1);
            }
        }

        pref = new PreferenceManager();

        //btnJoin.setEnabled(false);
        //btnIDcheck.setEnabled(false);

        editID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (checkID) {
                    checkID = false;
                    btnIDcheck.setImageResource(R.drawable.check_no);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnIDcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table = FirebaseDatabase.getInstance().getReference("NNfriendsDB/UserDB");
                table.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (editID.getText().toString().length() <= 0)
                            return;
                        Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                        while (child.hasNext()){
                            if(child.next().getKey().equals(interNum+editID.getText().toString())){
                                Toast.makeText(SignupActivity.this, "존재하는 아이디 입니다", Toast.LENGTH_SHORT).show();
                                table.removeEventListener(this);
                                editID.setText("");
                                //btnIDcheck.setBackgroundTintList();
                                checkID = false;
                                btnJoin.setEnabled(false);
                                return;
                            }
                        }
                        Toast.makeText(SignupActivity.this, "중복 확인 완료", Toast.LENGTH_SHORT).show();
                        btnIDcheck.setImageResource(R.drawable.check_ok);
                        checkID = true;
                        return;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        btnPINcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showPIN) {
                    showPIN = false;
                    //editPIN.setInputType(InputType.TYPE_MASK_CLASS); // 안보이게 해야됨
                    editPIN.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnPINcheck.setImageResource(R.drawable.hide);

                } else {
                    showPIN = true;
                    //editPIN.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editPIN.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btnPINcheck.setImageResource(R.drawable.show);
                }
            }
        });

        stateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.btnServant:
                        state = 0;
                        break;
                    case R.id.btnBeneficiary:
                        state = 1;
                        break;
                }
            }
        });

        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new AddressDialog(SignupActivity.this);

                TextView title = (TextView) dialog.findViewById(R.id.titleAddr);
                Spinner spinnerGu = (Spinner) dialog.findViewById(R.id.spinnerGu);
                final Spinner spinnerDong = (Spinner) dialog.findViewById(R.id.spinnerDong);
                final EditText detailAddr = (EditText) dialog.findViewById(R.id.detailAddr);
                Button close = (Button) dialog.findViewById(R.id.close);

                // 폰트
                typeface = Typeface.createFromAsset(getAssets(), "gozik.ttf");
                TextView textView1 = (TextView) dialog.findViewById(R.id.detailAddr);
                TextView textView2 = (TextView) dialog.findViewById(R.id.close);
                title.setTypeface(typeface);
                textView1.setTypeface(typeface);
                textView2.setTypeface(typeface);

                spinnerGu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        gu = (String) parent.getItemAtPosition(position);
                        //gu = " " + gu;
                        if (gu.equals("구")) {
                            gu = "";
                            dong = "";
                        }
                        spinnerDong.setAdapter(new ArrayAdapter<String>(
                                SignupActivity.this,
                                android.R.layout.simple_list_item_1,
                                getResources().getStringArray(entries[position])
                        ));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        gu = "";
                    }
                });
                spinnerDong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        dong = ((String) parent.getItemAtPosition(position));
                        //dong = " " + dong.substring(0, dong.length()-1);
                        Log.d("check", "dong : "+dong);

                        if (dong.equals("동"))
                            dong = "";
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        dong = "";
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 저장
                        detail = detailAddr.getText().toString();
                        Log.d("checkk", gu+","+dong+","+detail);
                        Log.d("checkk", "dismiss");
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

    }

    private void initFont() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "gozik.ttf");

        TextView textView1 = (TextView) findViewById(R.id.btnServant);
        TextView textView2 = (TextView) findViewById(R.id.btnBeneficiary);
        TextView textView3 = (TextView) findViewById(R.id.btnJoin);

        TextView textView4 = (TextView) findViewById(R.id.internum);
        TextView textView5 = (TextView) findViewById(R.id.editID);
        TextView textView6 = (TextView) findViewById(R.id.editPIN);
        TextView textView7 = (TextView) findViewById(R.id.editName);
        TextView textView8 = (TextView) findViewById(R.id.editSSN1);
        TextView textView9 = (TextView) findViewById(R.id.editSSN2);
        TextView textView10 = (TextView) findViewById(R.id.internum2);
        TextView textView11 = (TextView) findViewById(R.id.editHelperID);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);
        textView4.setTypeface(typeface);
        textView5.setTypeface(typeface);
        textView6.setTypeface(typeface);
        textView7.setTypeface(typeface);
        textView8.setTypeface(typeface);
        textView9.setTypeface(typeface);
        textView10.setTypeface(typeface);
        textView11.setTypeface(typeface);
    }

    public void btnClick(View view) {
        Intent intent;
        switch (view.getId()) {
//            case R.id.btnOCR:
//                intent = new Intent(SignupActivity.this, MainActivity.class);
//                startActivity(intent);
//                break;
            case R.id.btnJoin:
                if (joinCheck()) {
                    //intent = new Intent(SignupActivity.this, LoginActivity.class);
                    //startActivity(intent);
                    finish();
                    break;
                }
        }
    }

    public boolean joinCheck() {

        if (!checkID) {
            Toast.makeText(this, "아이디 중복검사가 필요합니다", Toast.LENGTH_SHORT).show();
            return false;
        }

        String SSN1 = editSSN1.getText().toString();
        String SSN2 = editSSN2.getText().toString();

        // 나이
        SimpleDateFormat curYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat curMonthDay = new SimpleDateFormat("MMdd");
        String userYear = "19"+SSN1.substring(0,2);
        String userMonthDay = SSN1.substring(2);

        Log.d("checkk", userYear+", "+userMonthDay);

        age = Integer.valueOf(curYear.format(new Date())) - Integer.valueOf(userYear);
        if (Integer.valueOf(userMonthDay) > Integer.valueOf(curMonthDay.format(new Date())))
            age = age - 1;


        // 성별
        if (SSN2.substring(0,1).equals("1"))
            gender = 0;
        else
            gender = 1;

        String vID = editHelperID.getText().toString();
        if (!vID.equals(""))
            vID = interNum+vID;

        User user = new User(interNum+editID.getText().toString(), editPIN.getText().toString(),
                editName.getText().toString(), age, gender, SSN1+SSN2, state,
                gu+" "+dong+" "+detail, "서울특별시", gu, dong, vID);

        table.child(interNum+editID.getText().toString()).setValue(user);


        // 지문 로그인을 위한 정보 저장
        pref.saveStringPref(SignupActivity.this, KEY_FINGER_ID, interNum+editID.getText().toString());
        pref.saveStringPref(SignupActivity.this, KEY_FINGER_PIN, editPIN.getText().toString());
        pref.saveStringPref(SignupActivity.this, KEY_FINGER_NAME, editName.getText().toString());
        pref.saveBooleanPref(SignupActivity.this, KEY_FINGER_FLAG, true);

        Toast.makeText(SignupActivity.this, "회원가입 하였습니다", Toast.LENGTH_SHORT).show();


        return true;
    }
}
