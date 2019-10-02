package me.miladjalali.commonutils;

import android.view.View;
import android.view.animation.CycleInterpolator;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;


public class AnimHelper {


    public static void pressAction(View view, int duration, float scaleX, float scaleY, @Nullable final Runnable after) {
        try {
            if (view.getScaleX() == 1) {
                ViewPropertyAnimatorCompat vp = ViewCompat.animate(view).setDuration(duration).scaleX(scaleX).scaleY(scaleY).setInterpolator(new CycleInterpolator(0.5f));
                vp.setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(final View view) {
                    }

                    @Override
                    public void onAnimationEnd(final View v) {
                        if (after != null)
                            after.run();
                    }

                    @Override
                    public void onAnimationCancel(final View view) {
                    }
                });

                vp.withLayer().start();
            }
        } catch (Exception e) {
        }
    }

}

