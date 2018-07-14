package com.selema.newproject.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.selema.newproject.R;

public class AppDialogs {


    public static void showRationalDialog(Context context, final PermissionListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.rational_message);
        builder.setPositiveButton(R.string.grant, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onPermissionGranted();
            }
        });
        builder.setNegativeButton(R.string.deny, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onPermissionDenied();
            }
        });
        builder.create().show();
    }
}
