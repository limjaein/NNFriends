package com.android.project.nnfriends_;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.project.nnfriends_.Classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class SignupActivity extends AppCompatActivity {

    EditText editID, editPIN, editName, editSSN1, editSSN2, editHelperID;
    Button btnIDcheck, btnPINcheck, btnJoin;
    RadioGroup stateGroup;
    RadioButton btnFingerprint;
    int state, age, gender;
    boolean fingerprint, checkID, showPIN;

    DatabaseReference table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initFont();
        init();
    }

    private void init() {
        editID = (EditText) findViewById(R.id.editID);
        editPIN = (EditText) findViewById(R.id.editPIN);
        editName = (EditText) findViewById(R.id.editName);
        editSSN1 = (EditText) findViewById(R.id.editSSN1);
        editSSN2 = (EditText) findViewById(R.id.editSSN2);
        editHelperID = (EditText) findViewById(R.id.editHelperID);
        btnIDcheck = (Button) findViewById(R.id.btnIDcheck);
        btnJoin = (Button) findViewById(R.id.btnJoin);
        btnPINcheck = (Button) findViewById(R.id.btnPINcheck);
        stateGroup = (RadioGroup) findViewById(R.id.stateGroup);
        btnFingerprint = (RadioButton) findViewById(R.id.btnFingerprint);
        fingerprint = false;
        checkID = false;
        showPIN = false;

        //btnJoin.setEnabled(false);
        //btnIDcheck.setEnabled(false);

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
                            if(child.next().getKey().equals(editID.getText().toString())){
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
                        //btnIDcheck.setBackgroundTintList();
                        checkID = true;
                        return;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
//        btnPINcheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (showPIN) {
//                    showPIN = false;
//                    editPIN.setInputType(InputType.TYPE_MASK_CLASS); // 안보이게 해야됨
//                } else {
//                    showPIN = true;
//                    editPIN.setInputType(InputType.TYPE_CLASS_NUMBER);
//                }
//            }
//        });

        editPIN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

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
        btnFingerprint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fingerprint = true;
                } else
                    fingerprint = false;
            }
        });

    }

    private void initFont() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "gozik.ttf");

        TextView textView1 = (TextView) findViewById(R.id.btnServant);
        TextView textView2 = (TextView) findViewById(R.id.btnBeneficiary);
        TextView textView3 = (TextView) findViewById(R.id.btnFingerprint);
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
                if (joinCheck()) {
                    intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
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

        age = 0; // age 처리
        gender = 0;
        final String SSN1 = editSSN1.getText().toString();
        final String SSN2 = editSSN2.getText().toString();
        if (!"2".equals(SSN2.substring(0,1).equals("2")))
            gender = 0;
        else
            gender = 1;

        table = FirebaseDatabase.getInstance().getReference("NNfriendsDB/UserDB");
        table.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // 회원가입 성공
                DatabaseReference member = table.child(editID.getText().toString());
                User user = new User(editID.getText().toString(), editPIN.getText().toString(),
                        editName.getText().toString(), Integer.valueOf(age), Integer.valueOf(gender), SSN1+SSN2,
                        state, "", "", "", "", editHelperID.getText().toString());
                member.setValue(user);
                Toast.makeText(SignupActivity.this, "회원가입 하였습니다", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return true;
    }
}
