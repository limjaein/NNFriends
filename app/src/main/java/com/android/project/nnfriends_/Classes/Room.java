package com.android.project.nnfriends_.Classes;

/**
 * Created by 조은미 on 2017-11-02.
 */

public class Room {
    private String Gu;              //활동 구
    private String Dong;            //활동 동
    private String groupDate;       //활동 날짜
    private String groupPlace;      //활동 장소
    private String groupContent;    //활동 내용
    private String Leader;          //방장 0_choeunmi 이런식으로 "matchnum_name"
    private String TeamNum;         //참여 팀 수
    private String Active;          //모집 중: 0, 마감:1
    private String Roomkey;         //방번호 -> 구_동_생성시간 (일단은 이렇게)

    public Room(){}
    public Room(String Gu, String Dong, String groupDate, String groupPlace, String groupContent){
        this.Gu = Gu;
        this.Dong = Dong;
        this.groupContent = groupContent;
        this.groupDate = groupDate;
        this.groupPlace = groupPlace;
    }
//
//    public String getLeader{return Leader;}
//    public String getTeamNum{return TeamNum;}
//    public String getActive{return Active;}
//    public String getRoomkey{return Roomkey;}
    public String getGu(){
        return Gu;
    }
    public String getDong(){
        return Dong;
    }
    public String getGroupDate(){
        return groupDate;
    }
    public String getGroupPlace(){
        return groupPlace;
    }
    public String getGroupContent(){
        return groupContent;
    }

    public void setLeader(String Leader){this.Leader = Leader;}
    public void setTeamNum(String TeamNum){this.TeamNum = TeamNum;}
    public void setActive(String Active){this.Active = Active;}
    public void setRoomkey(String Roomkey){this.Roomkey = Roomkey;}
    public void setGu(String Gu){
        this.Gu = Gu;
    }
    public void setDong(String Dong){
        this.Dong = Dong;
    }
    public void setGroupDate(String groupDate){
        this.groupDate = groupDate;
    }
    public void setGroupPlace(String groupPlace){
        this.groupPlace = groupPlace;
    }
    public void setGroupContent(String groupContent){
        this.groupContent = groupContent;
    }
}
