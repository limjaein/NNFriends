package com.android.project.nnfriends_;

import android.os.Bundle;
import android.view.Display;
import android.widget.ExpandableListView;

import com.android.project.nnfriends_.Classes.Call;
import com.android.project.nnfriends_.Classes.CallGroup;
import com.android.project.nnfriends_.Classes.Group;
import com.android.project.nnfriends_.Classes.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.android.project.nnfriends_.LoginActivity.KEY_USER_MATNUM;

public class FriendActivity extends MyActivity {
    private ExpandableListView exlistView;
    ArrayList<Group> MyGroupList;
    PreferenceManager pref;
    String matchNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        init();
    }

    private void init() {
        Display newDisplay = getWindowManager().getDefaultDisplay();
        int width = newDisplay.getWidth();

        ArrayList<CallGroup> DataList = new ArrayList<>();
        final ArrayList<Group> GroupList = new ArrayList<>();
        exlistView = (ExpandableListView)findViewById(R.id.callList);

        ///////////////////
        DatabaseReference gtable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/GroupDB");
        matchNum = pref.getIntPref(this, KEY_USER_MATNUM);
        Query query = gtable.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Group group = data.getValue(Group.class);
                    GroupList.add(group); //모든 그룹 가져오기
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        for(int i =0; i<GroupList.size(); i++){

        }

        DatabaseReference rtable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/RoomDB");
        ArrayList<Group>
        ///////////////////

        CallGroup cg = new CallGroup("어린이대공원"); // 장소

        ArrayList<Call> people = new ArrayList<>();
        people.add(new Call("JaeIn","01085286812"));
        people.add(new Call("EunMi","01051982889"));

        cg.setChild(people);

        CallGroup cg2 = new CallGroup("새천년관");

        ArrayList<Call> people2 = new ArrayList<>();
        people2.add(new Call("SeoungRi","01085286812"));
        people2.add(new Call("YoungSeo","01051982889"));

        cg2.setChild(people2);

        DataList.add(cg);
        DataList.add(cg2);

        ExpandAdapter adapter = new ExpandAdapter(getApplicationContext(),R.layout.call_grouplist,R.layout.call_childlist, DataList);
        //exlistView.setIndicatorBounds(width-50, width); //이 코드를 지우면 화살표 위치가 바뀐다.
        exlistView.setAdapter(adapter);
        //강제펼침
//        for(int i=0; i<adapter.getGroupCount();i++){
//            exlistView.expandGroup(i);
//        }
    }


}
