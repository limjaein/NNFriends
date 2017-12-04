package com.android.project.nnfriends_;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

public class RWSelectActivity extends MyActivity {
    //datepicker 부분
    public static int year;
    public static int day;
    public static int month;
    static Intent intent_diary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rwselect);

        initFont();
    }

    private void initFont() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "gozik.ttf");

        TextView textView1 = (TextView) findViewById(R.id.write_btn);
        TextView textView2 = (TextView) findViewById(R.id.read_btn);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
    }
    private void setDate() {
        //datepicker부분

        DialogFragment dialogfragment = new RWSelectActivity.DatePickerDialogTheme();
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
        }
        public void onDateSet(DatePicker view, int year, int month, int day){


            intent_diary.putExtra("year", year);
            intent_diary.putExtra("month", month+1);
            intent_diary.putExtra("day", day);

            startActivity(intent_diary);
        }

    }
    public void diaryClick(View view) {

        if(view.getId()==R.id.write_btn){
            intent_diary = new Intent(this, WDiaryActivity.class);
        }
        else if(view.getId()==R.id.read_btn){
            intent_diary = new Intent(this, RDiaryActivity.class);
        }

        setDate();

    }
}
