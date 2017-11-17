package com.android.project.nnfriends_;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.project.nnfriends_.Classes.Room;

import java.util.ArrayList;

/**
 * Created by 조은미 on 2017-11-02.
 */

public class GroupAdapter extends BaseAdapter {

    private ArrayList<Room> mRoom = new ArrayList<>();
    private Activity activity;

    public GroupAdapter(Activity activity, ArrayList<Room> mRoom){
        this.activity = activity;
        this.mRoom = mRoom;
    }
    @Override
    public int getCount() {
        return mRoom.size();
    }

    @Override
    public Room getItem(int i) {
        return mRoom.get(i);
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

        final Room room = mRoom.get(i);
        Log.d("checkk", "getView");

        TextView dateView = (TextView)view.findViewById(R.id.DateView);
        TextView placeView = (TextView)view.findViewById(R.id.PlaceView);
        TextView contentView = (TextView)view.findViewById(R.id.ContentView);

        dateView.setText(room.getGroupDate());
        placeView.setText(room.getGroupPlace());
        contentView.setText(room.getGroupContent());

        return view;
    }
}
