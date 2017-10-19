package com.android.project.nnfriends_;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class GroupActivity extends AppCompatActivity {

    final Context context = this;
    private Button GuBtn, DongBtn;
    public TextView GuTxt, DongTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        GuBtn = (Button) findViewById(R.id.gubtn);
        DongBtn = (Button) findViewById(R.id.dongbtn);
        GuTxt = (TextView)findViewById(R.id.GuTxt);
        DongTxt = (TextView)findViewById(R.id.DongTxt);
        String strName = "";
        GuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(GroupActivity.this);
                alertBuilder.setTitle("항목선택");

                final ArrayAdapter<String> GuAdapter = new ArrayAdapter<String>(
                        GroupActivity.this, android.R.layout.select_dialog_singlechoice
                );
                GuAdapter.add("광진구");
                GuAdapter.add("강서구");
                GuAdapter.add("강동구");


                alertBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });


                //Adapter 셋팅
                alertBuilder.setAdapter(GuAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //AlertDialog 안에 있는 AlertDialog
                        String strName = GuAdapter.getItem(i);
                        GuTxt.setText(strName);
                    }
                });
                alertBuilder.show();
            }
        });

        DongBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(GroupActivity.this);
                alertBuilder.setTitle("항목선택");

                final ArrayAdapter<String> Dongadapter = new ArrayAdapter<String>(
                        GroupActivity.this, android.R.layout.select_dialog_singlechoice
                );
                Dongadapter.add("자양동");
                Dongadapter.add("화양동");


                alertBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });


                //Adapter 셋팅
                alertBuilder.setAdapter(Dongadapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //AlertDialog 안에 있는 AlertDialog
                        String strName = Dongadapter.getItem(i);
                        DongTxt.setText(strName);
                    }
                });
                alertBuilder.show();
            }
        });

    }
}
