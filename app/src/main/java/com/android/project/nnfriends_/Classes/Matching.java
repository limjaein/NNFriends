package com.android.project.nnfriends_.Classes;

/**
 * Created by victory on 2017-10-19.
 */

public class Matching {
    private int matchNum;
    private int matchDate;
    private int rID;        // 수혜자(recipient)
    private int vID1;       // 봉사자(volunteer)
    private int vID2;

    public Matching(){}
    public Matching(int num, int date, int r, int v1, int v2){
        this.matchNum = num;
        this.matchDate = date;
        this.rID = r;
        this.vID1 = v1;
        this.vID2 = v2;
    }

    public int getMatchNum() {
        return matchNum;
    }

    public int getMatchDate() {
        return matchDate;
    }

    public int getrID() {
        return rID;
    }

    public int getvID1() {
        return vID1;
    }

    public int getvID2() {
        return vID2;
    }

    public void setMatchNum(int matchNum) {
        this.matchNum = matchNum;
    }

    public void setMatchDate(int matchDate) {
        this.matchDate = matchDate;
    }

    public void setrID(int rID) {
        this.rID = rID;
    }

    public void setvID1(int vID1) {
        this.vID1 = vID1;
    }

    public void setvID2(int vID2) {
        this.vID2 = vID2;
    }
}
