package com.angeloparenteapp.kmk_kissmarrykill.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Utils Class.
 */

public class Utils {

    //Method that will hide Status and Navigation Bar
    public static void hide(final View mContentView){
        final int UI_ANIMATION_DELAY = 100;

        Handler mHideHandler = new Handler();

        Runnable hideRunnable = new Runnable() {
            @SuppressLint("InlinedApi")
            @Override
            public void run() {
                mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        };

        mHideHandler.postDelayed(hideRunnable, UI_ANIMATION_DELAY);
    }

    /**
     * @param context Context
     * @return true or false depends on if the phone has internet connection.
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable());
    }

    public static void setTextViewCustomFont(Context context, TextView textView){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "myFont.otf");
        textView.setTypeface(font);
    }

    public static void setButtonCustomFont(Context context, Button button){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "myFont.otf");
        button.setTypeface(font);
    }
}