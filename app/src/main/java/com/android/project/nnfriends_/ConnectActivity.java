package com.android.project.nnfriends_;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

public class ConnectActivity extends MyActivity {
    Typeface typeface;

    Context context;
    DatabaseReference utable, mtable;

    PreferenceManager pref;

    User ser1, ser2, bef;
    Matching myMat;

    String userID, userMatnum;

    TextView gu, dong, state1, name1, age1, phone1, state2, name2, age2, phone2, titleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        context = this;
        pref = new PreferenceManager();
        utable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/UserDB");
        mtable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/MatchingDB");

        init();
        initFont();
        initTextView();
    }
    
    public void initTextView() {
        userID = pref.getStringPref(context, KEY_USER_ID);
        userMatnum = pref.getStringPref(context, KEY_USER_MATNUM);

        mtable.orderByKey().equalTo(userMatnum).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    myMat = data.getValue(Matching.class);
                    Log.d("victory", "matchNum:"+myMat.getMatchNum());
                    gu.setText(myMat.getGu());
                    dong.setText(myMat.getDong());
                }
                utable.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            User user = data.getValue(User.class);
                            if (user.getuID().equals(myMat.getvID1())) {
                                ser1 = user;
                            } else if (user.getuID().equals(myMat.getvID2())) {
                                ser2 = user;
                            } else if (user.getuID().equals(myMat.getrID())) {
                                bef = user;
                            }
                        }
                        Log.d("victory", ser1.getuID()+","+ser2.getuID()+","+bef.getuID());
                        if (userID.equals(userMatnum)) { // 수혜자
                            Log.d("victory userID, mat", userID+","+userMatnum);
                            state1.setText("Servant");
                            name1.setText(ser1.getName());
                            age1.setText(String.valueOf(ser1.getAge()));
                            phone1.setText(ser1.getuID());
                            state2.setText("Servant");
                            name2.setText(ser2.getName());
                            age2.setText(String.valueOf(ser2.getAge()));
                            phone2.setText(ser2.getuID());
                        } else {
                            Log.d("victory2 userID, mat", userID+","+userMatnum);
                            state1.setText("Beneficiary");
                            name1.setText(bef.getName());
                            age1.setText(String.valueOf(bef.getAge()));
                            phone1.setText(bef.getuID());
                            if (userID == ser1.getuID()) {
                                state2.setText("Servant");
                                name2.setText(ser2.getName());
                                age2.setText(String.valueOf(ser2.getAge()));
                                phone2.setText(ser2.getuID());
                            } else {
                                state2.setText("Servant");
                                name2.setText(ser1.getName());
                                age2.setText(String.valueOf(ser1.getAge()));
                                phone2.setText(ser1.getuID());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        

    }

    private void init() {
        
        gu = (TextView) findViewById(R.id.addGu);
        dong = (TextView) findViewById(R.id.addDong);
        state1 = (TextView) findViewById(R.id.state1);
        name1 = (TextView) findViewById(R.id.name1);
        age1 = (TextView) findViewById(R.id.agee1);
        phone1 = (TextView) findViewById(R.id.phone1);
        state2 = (TextView) findViewById(R.id.state2);
        name2 = (TextView) findViewById(R.id.name2);
        age2 = (TextView) findViewById(R.id.agee2);
        phone2 = (TextView) findViewById(R.id.phone2);
        titleView = (TextView) findViewById(R.id.addrTitle);
        
    }


    private void initFont() {
        typeface = Typeface.createFromAsset(getAssets(), "gozik.ttf");
        
        gu.setTypeface(typeface);
        dong.setTypeface(typeface);
        state1.setTypeface(typeface);
        state2.setTypeface(typeface);
        name1.setTypeface(typeface);
        name2.setTypeface(typeface);
        age1.setTypeface(typeface);
        age2.setTypeface(typeface);
        phone1.setTypeface(typeface);
        phone2.setTypeface(typeface);
        titleView.setTypeface(typeface);
    }

}
