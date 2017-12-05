package com.android.project.nnfriends_.Classes;

/**
 * Created by 조은미 on 2017-11-02.
 */

public class Room {
    private String Gu;              //활동 구
    private String Dong;            //활동 동
    private String groupDate;       //활동 날짜
    private String year;
    private String month;
    private String day;
    private String dayofweek;
    private String groupTime;       //활동 시간
    private String groupPlace;      //활동 장소
    private String groupContent;    //활동 내용
    private String LeaderName;          //방장이름
    private String LeaderID;            //방장아이디
    private int LeaderMatchNum;     //방장매칭번호
    private String TeamNum;         //참여 팀 수
    private String Active;          //모집 중: 0, 마감:1
    private String Roomkey;         //방번호, ex)201712020924MMMM_matchNum(생성자)
    private String attendNum;       //참가팀 수

    public Room(){}
    public Room(String RoomKey, String Active, String LeaderName, String LeaderID, int LeaderMatchNum,
                String TeamNum, String Gu, String Dong, String year, String month, String day, String dayofweek,
                String groupDate, String groupTime, String groupPlace, String groupContent, String attendNum){
        this.Roomkey = RoomKey;
        this.Active = Active;
        this.LeaderName = LeaderName;
        this.LeaderID = LeaderID;
        this.LeaderMatchNum = LeaderMatchNum;
        this.TeamNum = TeamNum;
        this.Gu = Gu;
        this.Dong = Dong;
        this.groupContent = groupContent;
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayofweek = dayofweek;
        this.groupDate = groupDate;
        this.groupTime = groupTime;
        this.groupPlace = groupPlace;
        this.attendNum = attendNum;


    }

    public String getLeaderName(){return LeaderName;}

    public String getLeaderID() {
        return LeaderID;
    }

    public int getLeaderMatchNum() {
        return LeaderMatchNum;
    }

    public String getTeamNum(){return TeamNum;}
    public String getActive(){return Active;}
    public String getRoomkey(){return Roomkey;}
    public String getGu(){
        return Gu;
    }
    public String getDong(){
        return Dong;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getDayofweek() {
        return dayofweek;
    }

    public String getGroupDate(){
        return groupDate;
    }

    public String getGroupTime() {
        return groupTime;
    }

    public String getGroupPlace(){
        return groupPlace;
    }
    public String getGroupContent(){
        return groupContent;
    }
    public String getAttendNum(){return attendNum;}

    public void setLeaderName(String LeaderName){this.LeaderName = LeaderName;}

    public void setLeaderID(String leaderID) {
        LeaderID = leaderID;
    }

    public void setLeaderMatchNum(int leaderMatchNum) {
        LeaderMatchNum = leaderMatchNum;
    }

    public void setTeamNum(String TeamNum){this.TeamNum = TeamNum;}
    public void setActive(String Active){this.Active = Active;}
    public void setRoomkey(String Roomkey){this.Roomkey = Roomkey;}
    public void setGu(String Gu){
        this.Gu = Gu;
    }
    public void setDong(String Dong){
        this.Dong = Dong;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setDayofweek(String dayofweek) {
        this.dayofweek = dayofweek;
    }

    public void setGroupDate(String groupDate){
        this.groupDate = groupDate;
    }

    public void setGroupTime(String groupTime) {
        this.groupTime = groupTime;
    }

    public void setGroupPlace(String groupPlace){
        this.groupPlace = groupPlace;
    }
    public void setGroupContent(String groupContent){
        this.groupContent = groupContent;
    }
    public void setAttendNum(String attendNum){this.attendNum = attendNum;}
}
