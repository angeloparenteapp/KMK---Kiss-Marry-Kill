package com.angeloparenteapp.kmk_kissmarrykill;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.angeloparenteapp.kmk_kissmarrykill.Utils.Utils;
import com.bumptech.glide.Glide;

public class Splash extends AppCompatActivity {

    public View mContentView;
    public Context mContext;

    final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContext = getApplicationContext();
        mContentView = findViewById(R.id.activity_splash);
        Utils.hide(mContentView);

        ImageView imageView = findViewById(R.id.splash);
        Glide.with(mContext).load(R.drawable.splash).into(imageView);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.hide(mContentView);
    }

    @Override
    protected void onRestart() {
        Utils.hide(mContentView);
        super.onRestart();
    }
}
