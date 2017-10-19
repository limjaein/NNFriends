package com.android.project.nnfriends_;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    //datepicker 부분
    public static int year;
    public static int day;
    public static int month;
    static Intent intent_diary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    // Example of a call to a native method
        Toast.makeText(this, stringFromJNI(), Toast.LENGTH_SHORT).show();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public void menuClick(View view) {
        Intent intent;

        switch(view.getId()){
            case R.id.connectBtn:
                intent = new Intent(this, ConnectActivity.class);
                startActivity(intent);
                break;
            case R.id.diaryBtn:
                intent_diary = new Intent(this, RWSelectActivity.class);
                setDate();
                break;
            case R.id.groupBtn:
                intent = new Intent(this, GroupActivity.class);
                startActivity(intent);
                break;
            case R.id.friendBtn:
                intent = new Intent();
                startActivity(intent);
                break;
        }
    }

    private void setDate() {
        //datepicker부분

        DialogFragment dialogfragment = new MenuActivity.DatePickerDialogTheme();
        dialogfragment.show(getFragmentManager(), "Theme");

    }
    public static class DatePickerDialogTheme extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,this,year,month,day);

            return datepickerdialog;
        }
        public void onResume(){
            super.onResume();
            //        Window window = getDialog().getWindow();
            //        window.setLayout(1200,1500);
            //        window.setGravity(Gravity.CENTER);
        }
        public void onDateSet(DatePicker view, int year, int month, int day){


            intent_diary.putExtra("year", year);
            intent_diary.putExtra("month", month+1);
            intent_diary.putExtra("day", day);

            startActivity(intent_diary);
        }

    }
}
