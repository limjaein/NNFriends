package com.android.project.nnfriends_;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.project.nnfriends_.Classes.DialogListAdapter;
import com.android.project.nnfriends_.Classes.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupActivity extends MyActivity {

    final Context context = this;
    private Button GuBtn, DongBtn;
    public TextView GuTxt, DongTxt;
    public ListView ActiveList;
    public Button addBtn;
    static Intent intent_add;


    ArrayList<Room> rooms = new ArrayList<>();

    ArrayList<String> GuList, DongList;
    int guNum;
    int[] entries = {
            R.array.dong0, R.array.dong1, R.array.dong2, R.array.dong3, R.array.dong4, R.array.dong5, R.array.dong6, R.array.dong7, R.array.dong8, R.array.dong9,
            R.array.dong10, R.array.dong11, R.array.dong12, R.array.dong13, R.array.dong14, R.array.dong15, R.array.dong16, R.array.dong17, R.array.dong18, R.array.dong19,
            R.array.dong20, R.array.dong21, R.array.dong22, R.array.dong23, R.array.dong24, R.array.dong25
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        /////////////////////////////////////
//        rooms.add(new Room("광진구","자양동","20171101, 13시","한아름볼링장", "볼링치기"));
//        rooms.add(new Room("광진구","화양동","20171103, 17시", "로니로티", "저녁먹기"));
//        rooms.add(new Room("광진구","화양동","20171107, 12시", "일감호", "산책하기"));
//        rooms.add(new Room("광진구","화양동","20171112, 15시", "화양동주민센터", "수다떨기"));
//        rooms.add(new Room("광진구","화양동","20171115, 14시", "노인정", "뜨개질"));

        /////////////////////////////////////

        ActiveList = (ListView)findViewById(R.id.activeList);
        addBtn = (Button)findViewById(R.id.addBtn);
        GuBtn = (Button) findViewById(R.id.gubtn);
        DongBtn = (Button) findViewById(R.id.dongbtn);
        GuTxt = (TextView)findViewById(R.id.GuTxt);
        DongTxt = (TextView)findViewById(R.id.DongTxt);
        GuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GuList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.gu)));


                final DialogListAdapter GuAdapter = new DialogListAdapter(GroupActivity.this, GuList);


                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(GroupActivity.this).setAdapter(GuAdapter,null);
                alertBuilder.setTitle("CHOICE");

                alertBuilder.setNegativeButton("CANCLE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });


                final AlertDialog alertDialog = alertBuilder.create();
                final ListView listView = alertDialog.getListView();
                alertDialog.show();
                listView.setAdapter(GuAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String strName = GuList.get(i);
                        GuTxt.setText(strName);
                        guNum=i;
                        alertDialog.dismiss();
                    }
                });
            }
        });
        DongBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DongList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(entries[guNum])));

                final DialogListAdapter DongAdapter = new DialogListAdapter(GroupActivity.this, DongList);

                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(GroupActivity.this).setAdapter(DongAdapter,null);
                alertBuilder.setTitle("CHOICE");
                alertBuilder.setNegativeButton("CANCLE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                final AlertDialog alertDialog = alertBuilder.create();
                final ListView listView = alertDialog.getListView();
                alertDialog.show();
                listView.setAdapter(DongAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String strName = DongList.get(i);
                        DongTxt.setText(strName);
                        alertDialog.dismiss();
                        if (!DongTxt.getText().equals("동")){
                            showList(GuTxt.getText().toString(), DongTxt.getText().toString());
                        }
                    }
                });
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_add = new Intent(getApplication(), RoomAddActivity.class);
                startActivityForResult(intent_add, 1000);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                Log.d("checkk", "onActivityResult");
                showList(GuTxt.getText().toString(), DongTxt.getText().toString());
            }
        }
    }

    public void showList(final String gu, final String dong){
        Log.d("checkk", "showList");
        final ArrayList<Room> selectDong = new ArrayList<>();
        DatabaseReference table = FirebaseDatabase.getInstance().getReference("NNfriendsDB/RoomDB");
        Query query = table.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Room room = data.getValue(Room.class);
                    if (room.getGu().equals(gu) && room.getDong().equals(dong)) {
                        selectDong.add(room);
                        Log.d("checkk", "datachange");
                    }
                }
                GroupAdapter mGroupAdapter = new GroupAdapter(GroupActivity.this, selectDong);
                ActiveList.setAdapter(mGroupAdapter);
                mGroupAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

