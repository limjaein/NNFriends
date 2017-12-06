package com.android.project.nnfriends_.Classes;

import java.util.ArrayList;

/**
 * Created by jaein on 2017-12-07.
 */

public class CallGroup {
    private ArrayList<Call> child;
    private String Position;
    private boolean publicCheck;
    //public -> true, private -> false

    CallGroup(){

    }
    public CallGroup(String Pos){
        Position = Pos;
        child = new ArrayList<>();
        publicCheck = false;
    }

    public void setChild(ArrayList<Call> child) {
        this.child = child;
    }
    public ArrayList<Call> getChild() {
        return child;
    }
    public String getPosition() {
        return Position;
    }
    public void setPosition(String position) {
        Position = position;
    }

    public void setPublicCheck(boolean publicCheck) {
        this.publicCheck = publicCheck;
    }
    public boolean isPublicCheck() {
        return publicCheck;
    }
}
