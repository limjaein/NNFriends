package com.android.project.nnfriends_.Classes;

/**
 * Created by 조은미 on 2017-11-02.
 */

public class Group {
    private String Gu;
    private String Dong;
    private String groupDate;
    private String groupPlace;
    private String groupContent;

    public Group(){}
    public Group(String Gu, String Dong, String groupDate, String groupPlace, String groupContent){
        this.Gu = Gu;
        this.Dong = Dong;
        this.groupContent = groupContent;
        this.groupDate = groupDate;
        this.groupPlace = groupPlace;
    }

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
