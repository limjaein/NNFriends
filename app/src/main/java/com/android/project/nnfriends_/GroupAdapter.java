package com.android.project.nnfriends_;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
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

import static com.android.project.nnfriends_.LoginActivity.KEY_USER_MATNUM;

/**
 * Created by 조은미 on 2017-11-02.
 */

public class GroupAdapter extends BaseAdapter {

    private ArrayList<Room> mRoom = new ArrayList<>();
    private Activity activity;
    private Room myRoom;
    int attendNum;
    private int teamNum;
    PreferenceManager pref;

    DatabaseReference gtable, rtable;
    String matchNum;

    int myFlag = 0; // 나의 참여 여부
    static int alertAttendNum = 0;
    static int alertTeamNum = 0;


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
        final TextView contentView = (TextView)view.findViewById(R.id.ContentView);
        final TextView peopleNum = (TextView)view.findViewById(R.id.PeopleNum);
        final ImageButton joinBtn = (ImageButton)view.findViewById(R.id.gJoinBtn);

        dateView.setText(myRoom.getGroupDate()+" "+myRoom.getGroupTime());
        placeView.setText(myRoom.getGroupPlace());
        contentView.setText(myRoom.getGroupContent());

        attendNum = Integer.parseInt(myRoom.getAttendNum());
        teamNum = Integer.parseInt(myRoom.getTeamNum());    //총 정원 수
        peopleNum.setText(String.valueOf(attendNum)+"("+attendNum*3+"people)"+"/"+String.valueOf(teamNum)+"("+teamNum*3+"people)");


        final String g_date = myRoom.getYear()+myRoom.getMonth()+myRoom.getDay();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final String c_date = sdf.format(new Date());
        Log.d("active0", g_date+", "+c_date);

        // database
        gtable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/GroupDB");
        rtable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/RoomDB");

        // user의 matchNum
        matchNum = pref.getStringPref(context, KEY_USER_MATNUM);

        // 버튼이미지확인
        int mat = pref.getIntPref(context, KEY_USER_MATNUM);

        if(Integer.parseInt(c_date)+1 >= Integer.parseInt(g_date)){
            Log.d("active2","님 바뀌세요?"+c_date+g_date);
            rtable.child(myRoom.getRoomkey()).child("active").setValue(String.valueOf("1"));
            joinBtn.setImageResource(R.drawable.end);
            joinBtn.setEnabled(false);
        }
        else{
            if(myRoom.getLeaderMatchNum()==mat){    //내가 만든 방
                if(myRoom.getActive().equals("0")){ //모집중
                    joinBtn.setImageResource(R.drawable.attend);
                    joinBtn.setEnabled(true);
                }
                else{   //마감
                    if(Integer.parseInt(c_date)+1 >= Integer.parseInt(g_date)){   //날짜 마감
                        joinBtn.setImageResource(R.drawable.end);
                        joinBtn.setEnabled(false);
                    }
                    else if(attendNum>=teamNum){    //날짜마감 x 인원마감
                        joinBtn.setImageResource(R.drawable.end);
                        joinBtn.setEnabled(true);
                    }
                }
            }
            else{   //내가 안만든 방

                // 내가 참여했는지 확인 0:참여안함 1:참여함
                myFlag = 0;
                final String key = myRoom.getRoomkey()+"_"+String.valueOf(matchNum);
                Query query = gtable.orderByKey().equalTo(key);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Group group = data.getValue(Group.class);
                            if (group.getKey().equals(key)) {
                                myFlag = 1;
                                Log.d("active4",key+"_"+myRoom.getRoomkey());
                                break;
                            }
                        }
                        //
                        if(myRoom.getActive().equals("0")){ //모집중


                            if(myFlag == 1){    //내가 참가한 방
                                joinBtn.setImageResource(R.drawable.attend);
                                joinBtn.setEnabled(true);
                            }
                            else{   //내가 참가 안한 방
                                joinBtn.setImageResource(R.drawable.join);
                                joinBtn.setEnabled(true);
                            }

                        }
                        else{   //마감
                            Log.d("active6","마감");
                            if(myFlag == 1){    //내가 참가한 방
                                if(Integer.parseInt(c_date)+1 >= Integer.parseInt(g_date)){   //날짜 마감
                                    joinBtn.setImageResource(R.drawable.end);
                                    joinBtn.setEnabled(false);
                                    Log.d("active6","내가참가, 날짜마감");
                                }
                                else if(attendNum>=teamNum){    //날짜마감 x 인원마감
                                    joinBtn.setImageResource(R.drawable.end);
                                    joinBtn.setEnabled(true);
                                    Log.d("active5","나참, 인원마감");
                                }
                            }
                            else{   //내가 참가 안한 방
                                joinBtn.setImageResource(R.drawable.end);
                                joinBtn.setEnabled(false);
                                Log.d("active7","내가참가안한방");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Log.d("active",String.valueOf(myFlag));


            }
        }




        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable d1 = joinBtn.getDrawable();
                Drawable d2= context.getResources().getDrawable(R.drawable.attend);
                Drawable d3 = context.getResources().getDrawable(R.drawable.end);
                Drawable d4 = context.getResources().getDrawable(R.drawable.join);
                Bitmap b_btn = ((BitmapDrawable)d1).getBitmap();
                Bitmap b_attend = ((BitmapDrawable)d2).getBitmap();
                Bitmap b_end = ((BitmapDrawable)d3).getBitmap();
                Bitmap b_join = ((BitmapDrawable)d4).getBitmap();

                int attendNum = Integer.parseInt(myRoom.getAttendNum());
                Log.d("attendNum","바뀌기 전"+String.valueOf(attendNum));
                int teamNum = Integer.parseInt(myRoom.getTeamNum());

                int mat = pref.getIntPref(context, KEY_USER_MATNUM);
                if(myRoom.getLeaderMatchNum()==mat) {    //내가 만든 방

                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setMessage("Are you sure you want to delete this room?")
                            .setCancelable(false)
                            .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(context, "DELETE", Toast.LENGTH_SHORT).show();
                                    //db삭제
                                    String rkey = myRoom.getRoomkey();
                                    String gkey = myRoom.getRoomkey()+"_"+matchNum;

                                    /////여기서 그 그룹에 참가한 모든 matchNum을 어떻게 다알아와서 삭제하지....??//////////
                                    //그룹 디비 에서 roomKey 가 rkey인 애들 다 삭제
                                    Query query = gtable.orderByChild("roomkey").equalTo(rkey);
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                                Group group = data.getValue(Group.class);
                                                gtable.child(group.getKey()).removeValue();
                                            }
                                            //
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });



                                    ///////////////////
//
//                                    gtable.child(gkey).removeValue();
                                    rtable.child(rkey).removeValue();
                                }
                            }).setNegativeButton("CANCLE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(context, "CANCLE", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                }
                else{   //내가 안만든 방
                    if(b_btn.equals(b_join)){   //버튼이 join일때
                        attendNum += 1;
                        alertAttendNum = attendNum;
                        alertTeamNum= teamNum;

                        /////해야할 거 cancle 했을 때는 attendNum 더해지면 안됨////

                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setMessage("Do you agree to disclose your personal information?")
                                .setCancelable(false)
                                .setPositiveButton("AGREE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(context, "AGREE", Toast.LENGTH_SHORT).show();
                                        //db저장
                                        String key = myRoom.getRoomkey()+"_"+matchNum;
                                        DatabaseReference groupRef = gtable.child(key);
                                        Group group= new Group(key, myRoom.getRoomkey(), String.valueOf(matchNum),String.valueOf(1));
                                        groupRef.setValue(group);

                                        rtable.child(myRoom.getRoomkey()).child("attendNum").setValue(String.valueOf(alertAttendNum));
                                        myRoom.setAttendNum(String.valueOf(alertAttendNum));
                                        Log.d("attendNum","바뀌고"+String.valueOf(alertAttendNum));

                                        if(alertAttendNum>=alertTeamNum){ //인원마감
                                            joinBtn.setImageResource(R.drawable.end);
                                            joinBtn.setEnabled(true);
                                            joinBtn.setScaleType(ImageView.ScaleType.FIT_XY);
                                            Toast.makeText(context, "ATTEND COMPLETE(인원마감)", Toast.LENGTH_SHORT).show();
                                            //activie 1로 바꾸기
                                            rtable.child(myRoom.getRoomkey()).child("active").setValue(String.valueOf("1"));
                                        }
                                        else{   //인원마감 아님
                                            joinBtn.setImageResource(R.drawable.attend);
                                            joinBtn.setEnabled(true);
                                            joinBtn.setScaleType(ImageView.ScaleType.FIT_XY);
                                            Toast.makeText(context, "ATTEND COMPLETE(노인원마감)", Toast.LENGTH_SHORT).show();
                                        }
                                        peopleNum.setText(String.valueOf(alertAttendNum)+"("+alertAttendNum*3+"people)"+"/"+String.valueOf(alertTeamNum)+"("+alertTeamNum*3+"people)");
                                    }
                                }).setNeutralButton("DISAGREE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //db저장
                                String key = myRoom.getRoomkey()+"_"+matchNum;
                                DatabaseReference groupRef = gtable.child(key);
                                Group group= new Group(key, myRoom.getRoomkey(), String.valueOf(matchNum),String.valueOf(0));
                                groupRef.setValue(group);

                                rtable.child(myRoom.getRoomkey()).child("attendNum").setValue(String.valueOf(alertAttendNum));
                                myRoom.setAttendNum(String.valueOf(alertAttendNum));
                                Log.d("attendNum","바뀌고"+String.valueOf(alertAttendNum));

                                if(alertAttendNum>=alertTeamNum){ //인원마감
                                    joinBtn.setImageResource(R.drawable.end);
                                    joinBtn.setEnabled(true);
                                    joinBtn.setScaleType(ImageView.ScaleType.FIT_XY);
                                    Toast.makeText(context, "ATTEND COMPLETE(인원마감)", Toast.LENGTH_SHORT).show();
                                    //activie 1로 바꾸기
                                    rtable.child(myRoom.getRoomkey()).child("active").setValue(String.valueOf("1"));
                                }
                                else{   //인원마감 아님
                                    joinBtn.setImageResource(R.drawable.attend);
                                    joinBtn.setEnabled(true);
                                    joinBtn.setScaleType(ImageView.ScaleType.FIT_XY);
                                    Toast.makeText(context, "ATTEND COMPLETE(노인원마감)", Toast.LENGTH_SHORT).show();
                                }
                                peopleNum.setText(String.valueOf(alertAttendNum)+"("+alertAttendNum*3+"people)"+"/"+String.valueOf(alertTeamNum)+"("+alertTeamNum*3+"people)");
                            }
                        }).setNegativeButton("CANCLE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(context, "CANCLE", Toast.LENGTH_SHORT).show();
                                joinBtn.setImageResource(R.drawable.join);
                                joinBtn.setEnabled(true);
                                alertAttendNum -= 1;
                                joinBtn.setImageResource(R.drawable.join);
                                joinBtn.setEnabled(true);
                                peopleNum.setText(String.valueOf(alertAttendNum)+"("+alertAttendNum*3+"people)"+"/"+String.valueOf(alertTeamNum)+"("+alertTeamNum*3+"people)");

                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    }
                    else{   //버튼이 end이거나 attend일때
                        attendNum -= 1;
                        //db삭제
                        String key = myRoom.getRoomkey()+"_"+matchNum;
                        gtable.child(key).removeValue();

                        //room attendNum 수정
                        rtable.child(myRoom.getRoomkey()).child("attendNum").setValue(String.valueOf(attendNum));
                        myRoom.setAttendNum(String.valueOf(attendNum));

                        if (b_btn.equals(b_end)){
                            //active 를0으로
                            rtable.child(myRoom.getRoomkey()).child("active").setValue(String.valueOf("0"));
                            Toast.makeText(context, "end 액티브 0으로", Toast.LENGTH_SHORT).show();
                        }
                        joinBtn.setImageResource(R.drawable.join);
                        joinBtn.setEnabled(true);
                        joinBtn.setScaleType(ImageView.ScaleType.FIT_XY);
                        peopleNum.setText(String.valueOf(attendNum)+"("+attendNum*3+"people)"+"/"+String.valueOf(teamNum)+"("+teamNum*3+"people)");
                        Toast.makeText(context, "참가 취소", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }


}
