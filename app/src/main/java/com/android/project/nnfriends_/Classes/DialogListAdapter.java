package com.android.project.nnfriends_.Classes;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.project.nnfriends_.R;

import java.util.ArrayList;

/**
 * Created by 조은미 on 2017-11-16.
 */
public class DialogListAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<String> dataList;

    public DialogListAdapter(Activity activity, ArrayList<String> dataList){
        this.activity = activity;
        this.dataList = dataList;
    }
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dialog_list_row, null);
        }

        final String gu = dataList.get(i);

        TextView textView = (TextView)view.findViewById(R.id.choiceTitle);

        textView.setText(gu);

        return view;
    }
}
