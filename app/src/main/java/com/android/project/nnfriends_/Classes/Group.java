package com.android.project.nnfriends_.Classes;

/**
 * Created by 조은미 on 2017-11-24.
 */

public class Group {
    private String Roomkey;         //방번호 -> 구_동_생성시간 (일단은 이렇게)
    private String matchNum;        //그룹 아이디

    public Group(){}
    public Group(String RoomKey, String matchNum){
        this.Roomkey = RoomKey;
        this.matchNum = matchNum;
    }

    public String getRoomkey(){
        return Roomkey;
    }
    public String getMatchNum(){
        return matchNum;
    }
    public void setRoomkey(String roomkey){
        this.Roomkey = roomkey;
    }
    public void setMatchNum(String matchNum){
        this.matchNum = matchNum;
    }
}
