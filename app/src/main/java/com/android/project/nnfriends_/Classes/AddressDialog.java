package com.android.project.nnfriends_.Classes;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import com.android.project.nnfriends_.R;

/**
 * Created by USER on 2017-12-14.
 */

public class AddressDialog extends Dialog implements View.OnClickListener {
    public AddressDialog(@NonNull Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_address);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
