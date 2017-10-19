package com.android.project.nnfriends_.Classes;

/**
 * Created by victory on 2017-10-19.
 */

public class Diary {
    private int diaryNum;
    private int vID;
    private int matchNum;
    private int vDate;
    private int vTime;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String answer5;

    public Diary(){}
    public Diary(int num, int vID, int matchNum, int date, int time, String a1, String a2, String a3, String a4, String a5){
        this.diaryNum = num;
        this.vID = vID;
        this.matchNum = matchNum;
        this.vDate = date;
        this.vTime = time;
        this.answer1 = a1;
        this.answer2 = a2;
        this.answer3 = a3;
        this.answer4 = a4;
        this.answer5 = a5;
    }

    public int getDiaryNum() {
        return diaryNum;
    }

    public int getvID() {
        return vID;
    }

    public int getMatchNum() {
        return matchNum;
    }

    public int getvDate() {
        return vDate;
    }

    public int getvTime() {
        return vTime;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public String getAnswer5() {
        return answer5;
    }

    public void setDiaryNum(int diaryNum) {
        this.diaryNum = diaryNum;
    }

    public void setvID(int vID) {
        this.vID = vID;
    }

    public void setMatchNum(int matchNum) {
        this.matchNum = matchNum;
    }

    public void setvDate(int vDate) {
        this.vDate = vDate;
    }

    public void setvTime(int vTime) {
        this.vTime = vTime;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public void setAnswer5(String answer5) {
        this.answer5 = answer5;
    }
}
