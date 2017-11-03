package com.android.project.nnfriends_;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.project.nnfriends_.Classes.Group;

import java.util.ArrayList;

/**
 * Created by 조은미 on 2017-11-02.
 */

public class GroupAdapter extends BaseAdapter {

    private ArrayList<Group> mGroup = new ArrayList<>();
    private Activity activity;

    public GroupAdapter(Activity activity, ArrayList<Group> mGroup){
        this.activity = activity;
        this.mGroup = mGroup;
    }
    @Override
    public int getCount() {
        return mGroup.size();
    }

    @Override
    public Group getItem(int i) {
        return mGroup.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.group_listview, null);
        }

        final Group group = mGroup.get(i);

        TextView dateView = (TextView)view.findViewById(R.id.DateView);
        TextView placeView = (TextView)view.findViewById(R.id.PlaceView);
        TextView contentView = (TextView)view.findViewById(R.id.ContentView);

        dateView.setText(group.getGroupDate());
        placeView.setText(group.getGroupPlace());
        contentView.setText(group.getGroupContent());

        return view;
    }
}
