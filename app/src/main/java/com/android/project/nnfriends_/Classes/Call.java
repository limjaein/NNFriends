package com.android.project.nnfriends_.Classes;

/**
 * Created by 조은미 on 2017-11-03.
 */

public class Call {
    private String Name;
    private String PhoneNum;

    public Call(){}
    public Call(String Name, String PhoneNum){
        this.Name = Name;
        this.PhoneNum = PhoneNum;
    }

    public String getName(){
        return Name;
    }
    public String getPhoneNum(){
        return PhoneNum;
    }
    public void setName(String Name){
        this.Name = Name;
    }
    public void setPhoneNum(String PhoneNum){
        this.PhoneNum = PhoneNum;
    }
}
