package me.miladjalali.commonutils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class DisplayHelper {

    public static int GetScreenWidthInPixel(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int GetScreenHeightInPixel(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

}
