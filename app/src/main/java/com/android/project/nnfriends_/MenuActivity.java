package com.android.project.nnfriends_;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.project.nnfriends_.Classes.Matching;
import com.android.project.nnfriends_.Classes.PreferenceManager;
import com.android.project.nnfriends_.Classes.User;
import com.android.project.nnfriends_.Classes.customDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.android.project.nnfriends_.LoginActivity.KEY_USER_ID;
import static com.android.project.nnfriends_.LoginActivity.KEY_USER_MATNUM;

public class MenuActivity extends MyActivity {

    Context context;
    DatabaseReference utable, mtable;
    PreferenceManager pref;
    //int userState;

    Typeface typeface;
    customDialog dialog;

    int ageMin, ageMax, genderFlag;

    User me;
    String userID, ser1, ser2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        context = this;
        pref = new PreferenceManager();
        utable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/UserDB");
        mtable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/MatchingDB");

        typeface = Typeface.createFromAsset(getAssets(), "gozik.ttf");


        userID = pref.getStringPref(context, KEY_USER_ID);
        Query query = utable.orderByKey().equalTo(userID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    //userState = user.getState();
                    me = user;
                    Log.d("victory", "userState:"+me.getState()+", "+me.getuID());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        initFont();

    // Example of a call to a native method
    }

    private void matchSetting() {
        //if문 처리 할것!
        dialog = new customDialog(this);
        TextView tv1 = (TextView) dialog.findViewById(R.id.selectAge);
        TextView tv2 = (TextView) dialog.findViewById(R.id.selectgender);
        RadioButton tv3 = (RadioButton) dialog.findViewById(R.id.age0);
        RadioButton tv4 = (RadioButton) dialog.findViewById(R.id.age1);
        RadioButton tv5 = (RadioButton) dialog.findViewById(R.id.age2);
        RadioButton tv6 = (RadioButton) dialog.findViewById(R.id.age3);
        RadioButton tv7 = (RadioButton) dialog.findViewById(R.id.gen0);
        RadioButton tv8 = (RadioButton) dialog.findViewById(R.id.gen1);
        RadioButton tv9 = (RadioButton) dialog.findViewById(R.id.gen2);
        tv1.setTypeface(typeface);
        tv2.setTypeface(typeface);
        tv3.setTypeface(typeface);
        tv4.setTypeface(typeface);
        tv5.setTypeface(typeface);
        tv6.setTypeface(typeface);
        tv7.setTypeface(typeface);
        tv8.setTypeface(typeface);
        tv9.setTypeface(typeface);

        RadioGroup radioAge = (RadioGroup) dialog.findViewById(R.id.age_select);
        RadioGroup radioGender = (RadioGroup) dialog.findViewById(R.id.gender_select);

        radioAge.setOnCheckedChangeListener(cclistener);
        radioGender.setOnCheckedChangeListener(cclistener);

        dialog.show();
    }

    public void matchBtnClick(View view) {


        // 매칭 세팅 저장
        Log.d("checkk", "age:"+ageMin+","+ageMax);
        Log.d("checkk", "gen:"+genderFlag);

        final String[] Servant = {"",""};

        //utable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/UserDB");
        utable.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ser1 = "";
                ser2 = "";

                int matFlag = 0;
                Log.d("checkk", "matFlag1:"+matFlag);

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Log.d("checkk", "matFlag2:"+matFlag);
                    if (matFlag > 1)
                        break;
                    Log.d("checkk", data.toString());
                    User user = data.getValue(User.class);
                    if (user.getState()==0 &&
                            user.getAddress_dong().equals(me.getAddress_dong()) &&
                            user.getAddress_gu().equals(me.getAddress_gu())) { // 봉사대기자의경우
                        Log.d("checkk", "check1");
                        int userAge = user.getAge();
                        if (ageMin <= userAge && ageMax >= userAge) { // 원하는 나이
                            Log.d("checkk", "check2");
                            if (genderFlag == -1 || (genderFlag == user.getGender())) { // 원하는 성별
                                Log.d("checkk", "check3");
                                //매칭!
                                if (matFlag == 0)
                                    ser1 = user.getuID();
                                else
                                    ser2 = user.getuID();
                                //Servant[matFlag] = user.getuID();
                                Log.d("checkk 매칭", ser1+","+ser2);
                                matFlag++;
                            }
                        }
                    }
                }

                if (matFlag >= 2) { // 매칭성공
                    Log.d("checkk", "check4");

                    Log.d("checkk ser1 ser2 bef", ser1+","+ser2+","+userID);

                            utable.child(ser1).child("state").setValue(2);
                            utable.child(ser1).child("matchNum").setValue(userID);
                            utable.child(ser2).child("state").setValue(2);
                            utable.child(ser2).child("matchNum").setValue(userID);
                            utable.child(userID).child("state").setValue(3);
                            utable.child(userID).child("matchNum").setValue(userID);
                            pref.saveStringPref(context, KEY_USER_MATNUM, userID);


                    Matching matching = new Matching(userID, "", me.getAddress_gu(), me.getAddress_dong(), userID, ser1, ser2);
                    mtable.child(userID).setValue(matching);

                    Toast.makeText(context, "Match", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        Log.d("checkk!!", ser1+","+ser2);
//        utable.child(ser1).child("state").setValue(2);
//        utable.child(ser1).child("matchNum").setValue(userID);
//        utable.child(ser2).child("state").setValue(2);
//        utable.child(ser2).child("matchNum").setValue(userID);
//        utable.child(userID).child("state").setValue(3);
//        utable.child(userID).child("matchNum").setValue(userID);


        dialog.dismiss();

        //Intent intent = new Intent(this, ConnectActivity.class);
        //startActivity(intent);
    }

    RadioGroup.OnCheckedChangeListener cclistener = new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if (radioGroup.getId() == R.id.age_select) {
                Log.d("checkk", "radio age");
                switch (i) {
                    case R.id.age0: // all
                        ageMin = 0;
                        ageMax = 150;
                        break;
                    case R.id.age1: // 60대
                        ageMin = 60;
                        ageMax = 69;
                        break;
                    case R.id.age2: // 70대
                        ageMin = 70;
                        ageMax = 79;
                        break;
                    case R.id.age3: // 80대
                        ageMin = 80;
                        ageMax = 150;
                        break;
                }
            } else if (radioGroup.getId() == R.id.gender_select) {
                Log.d("checkk", "radio gen");
                switch (i) {
                    case R.id.gen0: // all
                        genderFlag = -1;
                        break;
                    case R.id.gen1: // 여자
                        genderFlag = 1;
                        break;
                    case R.id.gen2: // 남자
                        genderFlag = 0;
                        break;
                }
            }
        }
    };

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
                Query query = utable.orderByKey().equalTo(userID);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            User user = data.getValue(User.class);
                            //userState = user.getState();
                            me = user;
                            Log.d("victory", "userState:"+me.getState()+", "+me.getuID());

                            if (me.getState() == 1) { // 매칭 전 수혜자
                                matchSetting(); // 매칭 다이얼로그
                            } else {
                                // 매칭전 봉사자,,
                                Intent intent = new Intent(context, ConnectActivity.class);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                Log.d("victory", "userState2:"+me.getState());

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
