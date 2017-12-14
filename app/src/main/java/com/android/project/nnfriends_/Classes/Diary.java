package com.android.project.nnfriends_.Classes;

import java.util.jar.Attributes;

/**
 * Created by victory on 2017-10-19.
 */

public class Diary {
    private String key; // ex)2017/12/02/09:24:MMMM_id
    private String vID;
    private String vName;
    private String matchNum;
    private String vYear;
    private String vMonth;
    private String vDay;
    private String wTime;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String answer5;

    public Diary(){}
    public Diary(String key, String vID, String vName, String matchNum, String year, String month, String day, String time, String a1, String a2, String a3, String a4, String a5){
        this.key = key;
        this.vID = vID;
        this.vName = vName;
        this.matchNum = matchNum;
        this.vYear = year;
        this.vMonth = month;
        this.vDay = day;
        this.wTime = time;
        this.answer1 = a1;
        this.answer2 = a2;
        this.answer3 = a3;
        this.answer4 = a4;
        this.answer5 = a5;
    }

    public String getKey() {
        return key;
    }

    public String getvID() {
        return vID;
    }

    public String getvName() {
        return vName;
    }

    public String getMatchNum() {
        return matchNum;
    }

    public String getvYear() {
        return vYear;
    }

    public String getvMonth() {
        return vMonth;
    }

    public String getvDay() {
        return vDay;
    }

    public String getwTime() {
        return wTime;
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

    public void setKey(String key) {
        this.key = key;
    }

    public void setvID(String vID) {
        this.vID = vID;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public void setMatchNum(String matchNum) {
        this.matchNum = matchNum;
    }

    public void setvYear(String vYear) {
        this.vYear = vYear;
    }

    public void setvMonth(String vMonth) {
        this.vMonth = vMonth;
    }

    public void setvDay(String vDay) {
        this.vDay = vDay;
    }

    public void setwTime(String vTime) {
        this.wTime = vTime;
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
