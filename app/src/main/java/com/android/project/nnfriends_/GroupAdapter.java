package com.android.project.nnfriends_;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.project.nnfriends_.Classes.Group;
import com.android.project.nnfriends_.Classes.PreferenceManager;
import com.android.project.nnfriends_.Classes.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import static com.android.project.nnfriends_.LoginActivity.KEY_USER_MATNUM;

/**
 * Created by 조은미 on 2017-11-02.
 */

public class GroupAdapter extends BaseAdapter {

    private ArrayList<Room> mRoom = new ArrayList<>();
    private Activity activity;
    private Room myRoom;
    private int attendNum;
    private int teamNum;
    PreferenceManager pref;

    DatabaseReference gtable, rtable;
    int matchNum;


    public GroupAdapter(Activity activity, ArrayList<Room> mRoom){
        this.activity = activity;
        this.mRoom = mRoom;
    }
    @Override
    public int getCount() {
        return mRoom.size();
    }

    @Override
    public Room getItem(int i) {
        return mRoom.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        final Context context = viewGroup.getContext();
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.group_listview, null);
        }

        myRoom = mRoom.get(i);
        Log.d("checkk", "getView");

        TextView dateView = (TextView)view.findViewById(R.id.DateView);
        TextView placeView = (TextView)view.findViewById(R.id.PlaceView);
        TextView contentView = (TextView)view.findViewById(R.id.ContentView);
        final TextView peopleNum = (TextView)view.findViewById(R.id.PeopleNum);
        final ImageButton joinBtn = (ImageButton)view.findViewById(R.id.gJoinBtn);

        dateView.setText(myRoom.getGroupDate());
        placeView.setText(myRoom.getGroupPlace());
        contentView.setText(myRoom.getGroupContent());

        //디비에서 해당 활동 참가하는 팀 수 가지고 와서 보여주기
        attendNum = Integer.parseInt(myRoom.getAttendNum());
        teamNum = Integer.parseInt(myRoom.getTeamNum());    //총 정원 수
        peopleNum.setText(String.valueOf(attendNum)+"("+attendNum*3+"people)"+"/"+String.valueOf(teamNum)+"("+teamNum*3+"people)");

        String groupDate = myRoom.getGroupDate();
        StringTokenizer st = new StringTokenizer(groupDate,",");
        final String g_date = st.nextToken();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final String c_date = sdf.format(new Date());

        // database
        gtable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/GroupDB");
        rtable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/RoomDB");

        // user의 matchNum
        matchNum = pref.getIntPref(context, KEY_USER_MATNUM);

        Query query = gtable.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Group group = data.getValue(Group.class);
                    int mat = pref.getIntPref(context, KEY_USER_MATNUM);
                    if (group.getMatchNum().equals(String.valueOf(mat))) {  //참가한 활동
                        if(Integer.parseInt(c_date)+1 == Integer.parseInt(g_date) ){
                            if(attendNum<teamNum){  //날짜마감이고 인원 마감x
                                joinBtn.setImageResource(R.drawable.end);
                                joinBtn.setEnabled(true);
                            }
                            else{ //날짜마감이고 인원마감
                                joinBtn.setImageResource(R.drawable.end);
                                joinBtn.setEnabled(true);
                            }
                        }
                        else{
                            if(attendNum<teamNum){  //날짜마감x 인원 마감x
                                joinBtn.setImageResource(R.drawable.attend);
                                joinBtn.setEnabled(true);
                            }
                            else{ //날짜마감x 인원마감
                                joinBtn.setImageResource(R.drawable.end);
                                joinBtn.setEnabled(true);
                            }
                        }
                    }
                    else{   //참가하지 않은 활동
                        if(Integer.parseInt(c_date)+1 == Integer.parseInt(g_date) ){
                            if(attendNum<teamNum){  //날짜마감이고 인원 마감x
                                joinBtn.setImageResource(R.drawable.end);
                                joinBtn.setEnabled(false);
                            }
                            else{ //날짜마감이고 인원마감
                                joinBtn.setImageResource(R.drawable.end);
                                joinBtn.setEnabled(false);
                            }
                        }
                        else{
                            if(attendNum<teamNum){  //날짜마감x 인원 마감x
                                joinBtn.setImageResource(R.drawable.join);
                                joinBtn.setEnabled(true);
                            }
                            else{ //날짜마감x 인원마감
                                joinBtn.setImageResource(R.drawable.end);
                                joinBtn.setEnabled(false);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myRoom.getActive().equals("0")){ //모집중
                    Drawable d1 = joinBtn.getDrawable();
                    Drawable d2 = context.getResources().getDrawable(R.drawable.attend);
                    Drawable d3 = context.getResources().getDrawable(R.drawable.end);
                    Bitmap b1 = ((BitmapDrawable)d1).getBitmap();
                    Bitmap b2 = ((BitmapDrawable)d2).getBitmap();
                    Bitmap b3 = ((BitmapDrawable)d3).getBitmap();

                    if(b1.equals(b2) || b1.equals(b3)){  //이미 참가한 활동이거나 end일때

                        //attendNum 감소
                        attendNum = attendNum - 1;
                        rtable.child(myRoom.getRoomkey()).child("attendNum").setValue(String.valueOf(attendNum));

                        //group db에서 삭제
                        String key = myRoom.getRoomkey()+"_"+matchNum;
                        gtable.child(key).removeValue();

                        peopleNum.setText(String.valueOf(attendNum)+"("+attendNum*3+"people)"+"/"+String.valueOf(teamNum)+"("+teamNum*3+"people)");

                        if(b1.equals(b2)){
                            Toast.makeText(context, "이미참가함", Toast.LENGTH_SHORT).show();
                            joinBtn.setImageResource(R.drawable.join);
                        }
                        if(b1.equals(b3)){
                            Toast.makeText(context, "마감된 활동 참가 취소함", Toast.LENGTH_SHORT).show();
                            if(Integer.parseInt(c_date)+1 == Integer.parseInt(g_date) ){
                                if(attendNum<teamNum){  //날짜마감이고 인원 마감x
                                    joinBtn.setImageResource(R.drawable.end);
                                    joinBtn.setEnabled(false);
                                    rtable.child(myRoom.getRoomkey()).child("active").setValue(String.valueOf("1"));
                                }
                                else{ //날짜마감이고 인원마감
                                    joinBtn.setImageResource(R.drawable.end);
                                    joinBtn.setEnabled(false);
                                    rtable.child(myRoom.getRoomkey()).child("active").setValue(String.valueOf("1"));
                                }
                            }
                            else{
                                if(attendNum<teamNum){  //날짜마감x 인원 마감x
                                    joinBtn.setImageResource(R.drawable.join);
                                    joinBtn.setEnabled(true);
                                    rtable.child(myRoom.getRoomkey()).child("active").setValue(String.valueOf("0"));
                                }
                                else{ //날짜마감x 인원마감
                                    joinBtn.setImageResource(R.drawable.join);
                                    joinBtn.setEnabled(true);
                                    rtable.child(myRoom.getRoomkey()).child("active").setValue(String.valueOf("0"));
                                }
                            }
                        }

                    }
                    else{ //참가하지 않은 활동
                        if(Integer.parseInt(c_date)+1 == Integer.parseInt(g_date) ){
                            if(attendNum<teamNum){  //날짜마감이고 인원 마감x
                                joinBtn.setImageResource(R.drawable.end);
                                joinBtn.setEnabled(false);
                                rtable.child(myRoom.getRoomkey()).child("active").setValue(String.valueOf("1"));
                            }
                            else{ //날짜마감이고 인원마감
                                joinBtn.setImageResource(R.drawable.end);
                                joinBtn.setEnabled(false);
                                rtable.child(myRoom.getRoomkey()).child("active").setValue(String.valueOf("1"));
                            }
                        }
                        else{
                            if(attendNum<teamNum){  //날짜마감x 인원 마감x
                                gtable.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        PreferenceManager pref = new PreferenceManager();

                                        String key = myRoom.getRoomkey()+"_"+matchNum;
                                        DatabaseReference groupRef = gtable.child(key);
                                        Group group= new Group(key, myRoom.getRoomkey(), String.valueOf(matchNum));
                                        groupRef.setValue(group);

                                        //joinBtn.setText("ATTEND");
                                        joinBtn.setImageResource(R.drawable.attend);
                                        joinBtn.setScaleType(ImageView.ScaleType.FIT_XY);
                                        Toast.makeText(context, "ATTEND COMPLETE", Toast.LENGTH_SHORT).show();

                                        //room에 해당하는 db내용 갱신 -> attendNum + 1
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                            }
                            else{ //날짜마감x 인원마감
                                joinBtn.setImageResource(R.drawable.end);
                                joinBtn.setEnabled(false);
                                rtable.child(myRoom.getRoomkey()).child("active").setValue(String.valueOf("0"));
                            }
                        }

                        attendNum = attendNum + 1;
                        rtable.child(myRoom.getRoomkey()).child("attendNum").setValue(String.valueOf(attendNum));
                        peopleNum.setText(String.valueOf(attendNum)+"("+attendNum*3+"people)"+"/"+String.valueOf(teamNum)+"("+teamNum*3+"people)");
                    }
                }
                else{   //마감

                    // 수정 필요
                    joinBtn.setImageResource(R.drawable.end);
                    joinBtn.setEnabled(false);

                    // 마감이지만 내가 참가신청한거면 취소할수있게하기!!
                }

            }
        });

        return view;
    }


}
