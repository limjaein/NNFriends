package com.android.project.nnfriends_;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.project.nnfriends_.Classes.Call;
import com.android.project.nnfriends_.Classes.CallGroup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by jaein on 2017-12-07.
 */

public class ExpandAdapter extends BaseExpandableListAdapter {
    private Context context;
    private int groupLayout = 0;
    private int chlidLayout = 0;
    private ArrayList<CallGroup> DataList;
    private LayoutInflater myinf = null;
    Typeface font;

    public ExpandAdapter(Context context,int groupLay,int chlidLay,ArrayList<CallGroup> DataList){
        this.DataList = DataList;
        this.groupLayout = groupLay;
        this.chlidLayout = chlidLay;
        this.context = context;
        this.myinf = (LayoutInflater)(this.context).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        font = Typeface.createFromAsset(context.getAssets(), "gozik.ttf");
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView == null){
            convertView = myinf.inflate(this.groupLayout, parent, false);
        }

        TextView groupName = (TextView)convertView.findViewById(R.id.groupName);
        Button publicBtn = (Button)convertView.findViewById(R.id.publicBtn);
        Button privateBtn = (Button)convertView.findViewById(R.id.privateBtn);
        TextView groupTime = (TextView)convertView.findViewById(R.id.groupTime);
        //버튼 포커싱 되면 child 안보임
        publicBtn.setFocusable(false);
        publicBtn.setFocusableInTouchMode(false);
        //버튼 포커싱 되면 child 안보임
        privateBtn.setFocusable(false);
        privateBtn.setFocusableInTouchMode(false);
        //버튼 때매 finalㅡㅡ
        final int groupPos = groupPosition;

        groupName.setText(DataList.get(groupPosition).getPosition());
        groupTime.setText(DataList.get(groupPosition).getDate());

        groupName.setTypeface(font);
        groupTime.setTypeface(font);

        //버튼 초기설정대로 바꾸기
        String check = DataList.get(groupPosition).getPublicCheck();
        if(check.equals("1")){//public
            privateBtn.setBackgroundColor(Color.parseColor("#ffffff")); // 하얀색
            publicBtn.setBackgroundColor(Color.parseColor("#b41abc9c")); // 검은색
            privateBtn.setTextColor(Color.parseColor("#b41abc9c"));
            publicBtn.setTextColor(Color.parseColor("#ffffff"));
        }else{//private
            publicBtn.setBackgroundColor(Color.parseColor("#ffffff")); // 하얀색
            privateBtn.setBackgroundColor(Color.parseColor("#b41abc9c")); // 검은색
            publicBtn.setTextColor(Color.parseColor("#b41abc9c"));
            privateBtn.setTextColor(Color.parseColor("#ffffff"));
        }

        //버튼 리스너 부분
        //오류나서 final 처리ㅡㅡ
        final View finalConvertView = convertView;
        publicBtn.setOnClickListener(new View.OnClickListener() {
            Button privateBtn = (Button) finalConvertView.findViewById(R.id.privateBtn);
            Button publicBtn = (Button) finalConvertView.findViewById(R.id.publicBtn);
            @Override
            public void onClick(View view) {
                privateBtn.setBackgroundColor(Color.parseColor("#ffffff")); // 하얀색
                publicBtn.setBackgroundColor(Color.parseColor("#b41abc9c")); // 검은색
                privateBtn.setTextColor(Color.parseColor("#b41abc9c"));
                publicBtn.setTextColor(Color.parseColor("#ffffff"));
                DataList.get(groupPos).setPublicCheck("1");

                DatabaseReference gtable;
                gtable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/GroupDB");
                gtable.child(DataList.get(groupPos).getGroupKey()).child("infoFlag").setValue(String.valueOf("1"));
                DataList.get(groupPos).setPublicCheck("1");
                notifyDataSetChanged();
            }
        });
        privateBtn.setOnClickListener(new View.OnClickListener() {
            Button privateBtn = (Button) finalConvertView.findViewById(R.id.privateBtn);
            Button publicBtn = (Button) finalConvertView.findViewById(R.id.publicBtn);
            @Override
            public void onClick(View view) {
                publicBtn.setBackgroundColor(Color.parseColor("#ffffff")); // 하얀색
                privateBtn.setBackgroundColor(Color.parseColor("#b41abc9c")); // 검은색
                publicBtn.setTextColor(Color.parseColor("#b41abc9c"));
                privateBtn.setTextColor(Color.parseColor("#ffffff"));
                DataList.get(groupPos).setPublicCheck("0");

                DatabaseReference gtable;
                gtable = FirebaseDatabase.getInstance().getReference("NNfriendsDB/GroupDB");
                gtable.child(DataList.get(groupPos).getGroupKey()).child("infoFlag").setValue(String.valueOf("0"));
                DataList.get(groupPos).setPublicCheck("0");
                notifyDataSetChanged();
            }
        });


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView == null){
            convertView = myinf.inflate(this.chlidLayout, parent, false);
        }

        final ArrayList<Call> people = DataList.get(groupPosition).getChild();
        Log.d("groupList","groupPos"+"_"+groupPosition);
        Button callBtn = (Button) convertView.findViewById(R.id.CallBtn);
        //버튼 포커싱 되면 child 안보임
        callBtn.setFocusable(false);
        callBtn.setFocusableInTouchMode(false);
        TextView childName = (TextView)convertView.findViewById(R.id.ChildNameView);
        TextView childPhone = (TextView)convertView.findViewById(R.id.ChildPhoneView);
        childName.setText(people.get(childPosition).getName());
        Log.d("infoFlag", people.get(childPosition).getName()+"_"+people.get(childPosition).getInfoFalg());
        if(people.get(childPosition).getInfoFalg().equals("1")){
            childPhone.setText(people.get(childPosition).getPhoneNum());
        }
        else{
            childPhone.setText("비공개");
        }
        childName.setTypeface(font);
        childPhone.setTypeface(font);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //childposition 0으로일단
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + people.get(0).getPhoneNum()));
                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return DataList.get(groupPosition).getChild().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return DataList.get(groupPosition).getChild().size();
    }

    @Override
    public CallGroup getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return DataList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return DataList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

}