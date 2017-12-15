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
    ArrayList<Group> MyGroupList= new ArrayList<>();
    ArrayList<Room> RoomList= new ArrayList<>();
    ArrayList<Room> AttendRoomList = new ArrayList<>();
    ArrayList<CallGroup> callGroups= new ArrayList<>();
    ArrayList<Call> people = new ArrayList<>();
    ArrayList<Group> GroupList = new ArrayList<>();
    ArrayList<User> UserList = new ArrayList<>();
    PreferenceManager pref;
    String matchNum;
    CallGroup cg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        init();
    }

    private void init() {
        Display newDisplay = getWindowManager().getDefaultDisplay();
        int width = newDisplay.getWidth();


        exlistView = (ExpandableListView)findViewById(R.id.callList);

        ///////////////////
        //모든 그룹 가져오기
        DatabaseReference gtable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/GroupDB");
        matchNum = pref.getStringPref(this, KEY_USER_MATNUM);
        Query query = gtable.orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Group group = data.getValue(Group.class);
                    GroupList.add(group); //모든 그룹 가져오기
                    Log.d("groupList", group.getRoomkey());
                }

                Log.d("groupList", GroupList.get(0).getRoomkey());
                if(GroupList.size() != 0){
                    for(int i =0; i<GroupList.size(); i++){
                        if(matchNum.equals(GroupList.get(i).getMatchNum())){
                            MyGroupList.add(GroupList.get(i));      //내가 참가한 그룹들
                        }
                    }
                }


                DatabaseReference rtable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/RoomDB");
                Query query1 = rtable.orderByKey();
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Room room = data.getValue(Room.class);
                            RoomList.add(room);
                        }

                        Log.d("groupList","RoomList"+"_"+RoomList.get(0).getRoomkey());
                        ///내가 참석한 방 정보 저장
                        if(RoomList.size() != 0){
                            Log.d("groupList","들어감");
                            for(int i = 0; i<GroupList.size(); i++){
                                if(matchNum.equals(GroupList.get(i).getMatchNum())){    //내가 참가한 그룹
                                    for (int j = 0; j<RoomList.size(); j++){
                                        if(GroupList.get(i).getRoomkey().equals(RoomList.get(j).getRoomkey())){
                                            AttendRoomList.add(RoomList.get(j));
                                        }
                                    }
                                }
                            }
                        }

                        if(AttendRoomList.size() != 0){ //내가 참석한 룸의 정보들
                            DatabaseReference utable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/UserDB");
                            Query query2 = utable.orderByKey();

                            query2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        User user = data.getValue(User.class);
                                        UserList.add(user);
                                    }

                                    if(UserList.size()!=0){
                                        for (int i = 0; i < AttendRoomList.size(); i++) {
                                            ArrayList<Call> people2 = new ArrayList<Call>();
                                            String publicCheck ="0";
                                            Log.d("groupList","peoplesize_"+people2.size());
                                            if (AttendRoomList.get(i).getActive().equals("1")) {  //마감된 방이고
                                                Log.d("groupList","1"+AttendRoomList.get(i).getRoomkey()+"_내가참석하고마감된방");
                                                  //해당 장소의 그룹 생성
                                                Log.d("groupList","그룹생성"+AttendRoomList.get(i).getGroupPlace());
                                                for (int j=0; j<UserList.size(); j++){
                                                    for(int a=0; a<GroupList.size(); a++){
                                                        if (GroupList.get(a).getRoomkey().equals(AttendRoomList.get(i).getRoomkey())){
                                                            if(UserList.get(j).getMatchNum().equals(GroupList.get(a).getMatchNum())){
                                                                if (!(UserList.get(j).getMatchNum().equals(matchNum))){
                                                                    User user = UserList.get(j);
                                                                    people2.add(new Call(user.getName(), user.getuID(), GroupList.get(a).getInfoFlag()));
                                                                    Log.d("groupList",AttendRoomList.get(i).getGroupPlace()+"_"+user.getName());
                                                                }
                                                                else{
                                                                    publicCheck = GroupList.get(a).getInfoFlag();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                                callGroups.add(new CallGroup(AttendRoomList.get(i).getGroupPlace(),people2,AttendRoomList.get(i).getRoomkey()+"_"+matchNum, publicCheck));
                                                Log.d("groupList",callGroups.get(0).getPosition());
                                                Log.d("groupList",callGroups.get(0).getChild().get(0).getName());
                                            }
                                        }

                                        if(callGroups.size() != 0){

                                            ExpandAdapter adapter = new ExpandAdapter(getApplicationContext(),R.layout.call_grouplist,R.layout.call_childlist, callGroups);
                                            exlistView.setAdapter(adapter);
                                        }

                                    }




                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                        });


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


}
