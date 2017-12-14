package com.android.project.nnfriends_.Classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.project.nnfriends_.R;

import java.util.ArrayList;

/**
 * Created by 조은미 on 2017-11-03. 
 */

public class CallAdapter extends BaseAdapter {

    private ArrayList<Call> mCall = new ArrayList<>();
    private Activity activity;

    public CallAdapter(Activity activity, ArrayList<Call> mCall) {
        this.activity = activity;
        this.mCall = mCall;
    }

    @Override
    public int getCount() {
        return mCall.size();
    }

    @Override
    public Call getItem(int i) {
        return mCall.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, final View convertView, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.call_listview, null);
        }

        final Call call = mCall.get(i);

        TextView nameView = (TextView) view.findViewById(R.id.NameView);
        TextView phoneView = (TextView) view.findViewById(R.id.PhoneView);
        Button callBtn = (Button) view.findViewById(R.id.CallBtn);

        nameView.setText(call.getName());
        phoneView.setText(call.getPhoneNum());
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + call.getPhoneNum()));
                if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling 
                    //    ActivityCompat#requestPermissions 
                    // here to request the missing permissions, and then overriding 
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions, 
                    //                                          int[] grantResults) 
                    // to handle the case where the user grants the permission. See the documentation 
                    // for ActivityCompat#requestPermissions for more details. 
                    return;
                }
                context.startActivity(intent);
            }
        });

        return view;
    }
}
