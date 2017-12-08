package com.android.project.nnfriends_.Classes;

/**
 * Created by 조은미 on 2017-11-24.
 */

public class Group {
    private String key;         // RoomKey_matchNum
    private String Roomkey;     //방번호
    private String matchNum;    //그룹 아이디
    private String infoFlag;    //정보 공개 여부 0:공개 안함, 1: 공개함

    public Group(){}
    public Group(String key, String RoomKey, String matchNum, String infoFlag){
        this.key = key;
        this.Roomkey = RoomKey;
        this.matchNum = matchNum;
        this.infoFlag = infoFlag;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRoomkey(){
        return Roomkey;
    }

    public String getInfoFlag(){
        return infoFlag;
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
    public void setInfoFlag(String infoFlag){this.infoFlag = infoFlag;}
}
