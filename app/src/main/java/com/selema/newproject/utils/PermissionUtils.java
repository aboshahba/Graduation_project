package com.selema.newproject.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionUtils implements PermissionListener {

    public static final int WRITE_EXTERNAL_STORAGE_CODE = 101;

    private static PermissionUtils permissionUtils;
    private Context context;
    private PermissionListener listener;

    private PermissionUtils(Context context, PermissionListener listener){
        this.context = context;
        this.listener = listener;
    }

    public static PermissionUtils getInstance(Context context, PermissionListener listener){
        if (permissionUtils == null)
            permissionUtils = new PermissionUtils(context, listener);
        return permissionUtils;
    }

    public void requestReadPermission(){
        if (ContextCompat.checkSelfPermission(this.context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                AppDialogs.showRationalDialog(context, this);
            }
            else{
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE_CODE);
            }
        }
        else{
            listener.onPermissionGranted();
        }
    }

    @Override
    public void onPermissionGranted() {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                WRITE_EXTERNAL_STORAGE_CODE);
    }

    @Override
    public void onPermissionDenied() {

    }
}
