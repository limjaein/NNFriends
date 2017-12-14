package com.android.project.nnfriends_.Classes;

/**
 * Created by victory on 2017-10-19.
 */

public class Matching {
    private String matchNum;  // 수혜자 아이디
    private String matchDate;
    private String gu;
    private String dong;
    private String rID;        // 수혜자(recipient)
    private String vID1;       // 봉사자(volunteer)
    private String vID2;

    public Matching(){}
    public Matching(String num, String date, String gu, String dong, String r, String v1, String v2){
        this.matchNum = num;
        this.matchDate = date;
        this.gu = gu;
        this.dong = dong;
        this.rID = r;
        this.vID1 = v1;
        this.vID2 = v2;
    }

    public String getDong() {
        return dong;
    }

    public String getGu() {
        return gu;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public void setGu(String gu) {
        this.gu = gu;
    }

    public String getMatchNum() {
        return matchNum;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public String getrID() {
        return rID;
    }

    public String getvID1() {
        return vID1;
    }

    public String getvID2() {
        return vID2;
    }

    public void setMatchNum(String matchNum) {
        this.matchNum = matchNum;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public void setrID(String rID) {
        this.rID = rID;
    }

    public void setvID1(String vID1) {
        this.vID1 = vID1;
    }

    public void setvID2(String vID2) {
        this.vID2 = vID2;
    }
}
