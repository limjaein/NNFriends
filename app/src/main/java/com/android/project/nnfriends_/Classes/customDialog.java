package com.android.project.nnfriends_.Classes;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import com.android.project.nnfriends_.R;

/**
 * Created by jaein on 2017-10-27.
 */

public class customDialog extends Dialog implements View.OnClickListener{
    public customDialog(@NonNull Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.matching_dialog);

    }

    @Override
    public void onClick(View view) {
            dismiss();

    }



}
