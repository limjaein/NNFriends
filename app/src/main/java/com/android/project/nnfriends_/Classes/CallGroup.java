package com.android.project.nnfriends_.Classes;

import java.util.ArrayList;

/**
 * Created by jaein on 2017-12-07.
 */

public class CallGroup {
    private ArrayList<Call> child;
    private String Position;    //장소 제목
    private String Date;    //날짜
    private String groupKey;    //그룹 키 정보
    private String publicCheck;    //공개여부`
    //public -> true, private -> false

    CallGroup(){
    }
    public CallGroup(String Pos){
        Position = Pos;
        child = new ArrayList<>();
        publicCheck = "0";
    }

    public CallGroup(String Pos, String Date, ArrayList<Call> people, String groupKey, String publicCheck){
        Position = Pos;
        this.Date = Date;
        child = people;
        this.groupKey = groupKey;
        this.publicCheck = publicCheck;
    }

    public void setDate(String Date){this.Date = Date;}
    public String getDate(){return Date;}
    public void setChild(ArrayList<Call> child) {
        this.child = child;
    }
    public ArrayList<Call> getChild() {
        return child;
    }
    public String getPosition() {
        return Position;
    }
    public String getGroupKey(){return groupKey;}
    public void setPosition(String position) {
        Position = position;
    }
    public void setGroupKey(String groupKey){this.groupKey = groupKey;}
    public void setPublicCheck(String publicCheck) {
        this.publicCheck = publicCheck;
    }
    public String getPublicCheck() {
        return publicCheck;
    }
}
