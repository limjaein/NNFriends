package com.android.project.nnfriends_.Classes;

/**
 * Created by 조은미 on 2017-11-03.
 */

public class Call {
    private String Name;
    private String PhoneNum;
    private String infoFalg;

    public Call(){}
    public Call(String Name, String PhoneNum, String infoFalg){
        this.Name = Name;
        this.PhoneNum = PhoneNum;
        this.infoFalg = infoFalg;
    }

    public String getName(){
        return Name;
    }
    public String getPhoneNum(){
        return PhoneNum;
    }
    public String getInfoFalg(){return infoFalg;}
    public void setName(String Name){
        this.Name = Name;
    }
    public void setPhoneNum(String PhoneNum){
        this.PhoneNum = PhoneNum;
    }
    public void setInfoFalg(String infoFalg){this.infoFalg = infoFalg;}
}
