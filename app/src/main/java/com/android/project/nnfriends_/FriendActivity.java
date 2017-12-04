package com.android.project.nnfriends_;

import android.os.Bundle;
import android.widget.ListView;

import com.android.project.nnfriends_.Classes.Call;

import java.util.ArrayList;

public class FriendActivity extends MyActivity {

    ArrayList<Call> calls = new ArrayList<>();
    ListView callList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        callList = (ListView)findViewById(R.id.callList);

        calls.add(new Call("ChoEunMi","010-5198-2889"));
        calls.add(new Call("LimJaeIn","010-8528-6812"));
        calls.add(new Call("KimYoungSeo","010-6564-3691"));
        calls.add(new Call("HeoSeungRi","010-2767-9287"));

        CallAdapter mCallAdapter = new CallAdapter(FriendActivity.this, calls);
        callList.setAdapter(mCallAdapter);
    }

}
