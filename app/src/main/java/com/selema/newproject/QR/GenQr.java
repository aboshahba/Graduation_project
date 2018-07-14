package com.selema.newproject.QR;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.selema.newproject.R;

import net.glxn.qrgen.android.QRCode;

public class
GenQr extends AppCompatActivity {
    Bitmap myBitmap;

    ImageView myImage;
    SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_qr);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        String ID = settings.getString("phoneNumber", "01212260464");

        myBitmap = QRCode.from(ID).bitmap();
        myImage = (ImageView) findViewById(R.id.imageView);
        myImage.setImageBitmap(myBitmap);
    }
}
