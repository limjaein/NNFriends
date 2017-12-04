package com.android.project.nnfriends_;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by jaein on 2017-12-01.
 */

public class CustomApp extends Application {
    boolean isSetPasswd;
    String m_preActivity;

    @Override
    public void onCreate() {
        super.onCreate();

        m_preActivity = "Activity";

        Log.d("check","AppCreate");
        Log.d("check", getPreActivity());

        resetIsSetPassword();
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public boolean getIsSetPassword() {
        return isSetPasswd;
    }

    public void resetIsSetPassword() {
        SharedPreferences pf = getSharedPreferences("Setting", 0);
        isSetPasswd = pf.getBoolean("isSetPassword", true);
    }

    public String getPreActivity(){
        return m_preActivity;
    }

    public void setPreActivity(String preActivity){
        m_preActivity = preActivity;
    }

}
