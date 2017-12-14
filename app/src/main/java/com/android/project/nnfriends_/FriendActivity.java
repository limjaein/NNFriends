package com.android.project.nnfriends_;

import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ExpandableListView;

import com.android.project.nnfriends_.Classes.Call;
import com.android.project.nnfriends_.Classes.CallGroup;
import com.android.project.nnfriends_.Classes.Group;
import com.android.project.nnfriends_.Classes.PreferenceManager;
import com.android.project.nnfriends_.Classes.Room;
import com.android.project.nnfriends_.Classes.User;
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
    ArrayList<Room> AttendRoomList;
    ArrayList<CallGroup> callGroups;
    ArrayList<Call> people;
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

        final ArrayList<Group> GroupList = new ArrayList<>();
        exlistView = (ExpandableListView)findViewById(R.id.callList);

        ///////////////////
        //모든 그룹 가져오기
        DatabaseReference gtable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/GroupDB");
        matchNum = pref.getStringPref(this, KEY_USER_MATNUM);
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

        //내가 참가한 그룹들
        for(int i =0; i<GroupList.size(); i++){
            if(matchNum.equals(GroupList.get(i).getMatchNum())){
                MyGroupList.add(GroupList.get(i));      //내가 참가한 그룹들
            }
        }

        //내가 참가한 방 정보 가져오기
        DatabaseReference rtable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/RoomDB");
        Query query1 = rtable.orderByKey();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Room room = data.getValue(Room.class);
                    for (int i=0; i<MyGroupList.size(); i++){
                        if(MyGroupList.get(i).getRoomkey().equals(room.getRoomkey())){
                            AttendRoomList.add(room);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //참가한 방이 마감된 방인지 검사 후 나와 같이 참가한 사람들의 matchNum을 리스트에 저장
        for(int i=0; i<AttendRoomList.size(); i++){
            if(AttendRoomList.get(i).getActive().equals("1")) { //마감된 방이면
                CallGroup cg = new CallGroup(AttendRoomList.get(i).getGroupPlace());    //해당 장소의 그룹 생성
                people.clear();
                //이방에 참가한 사람들의 matchNum을 저장
                for (int j = 0; j<GroupList.size(); j++){
                    if(AttendRoomList.get(i).getRoomkey().equals(GroupList.get(j).getRoomkey())){
                        if(GroupList.get(j).getMatchNum().equals(matchNum)){
                            Log.d("checkk","내방");
                        }
                        else{
                            DatabaseReference utable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/UserDB");
                            Query query2 = utable.orderByKey().equalTo(GroupList.get(j).getMatchNum());
                            final Group currentGroup = GroupList.get(j);
                            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        User user = data.getValue(User.class);
                                        people.add(new Call(user.getName(), user.getuID(), currentGroup.getInfoFlag()));
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                    }
                }
                cg.setChild(people);
                callGroups.add(cg);
            }
        }

        //matchNum


        ///////////////////


        ExpandAdapter adapter = new ExpandAdapter(getApplicationContext(),R.layout.call_grouplist,R.layout.call_childlist, callGroups);
        //exlistView.setIndicatorBounds(width-50, width); //이 코드를 지우면 화살표 위치가 바뀐다.
        exlistView.setAdapter(adapter);
        //강제펼침
//        for(int i=0; i<adapter.getGroupCount();i++){
//            exlistView.expandGroup(i);
//        }
    }


}
