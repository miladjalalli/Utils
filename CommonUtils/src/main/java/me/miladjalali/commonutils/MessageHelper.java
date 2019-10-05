package me.miladjalali.commonutils;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MessageHelper {

    public enum ToastPosition {
        TOP,
        MIDDLE,
        BOTTUM
    }

    public static void showToast(final Context context, final String description, ToastPosition position) {

        View layout = LayoutInflater.from(context).inflate(R.layout.custom_toast, null, false);

        TextView text = layout.findViewById(R.id.txtDescription);
        text.setText(description);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        switch (position) {

            case TOP:
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 200);
                break;

            case MIDDLE:
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                break;

            case BOTTUM:
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, -200);
                break;

        }

        toast.show();

    }

    public static void showSnake(final Context context, View view, String description) {
        Snackbar snackbar = Snackbar.make(view, description, Snackbar.LENGTH_LONG)
                .setActionTextColor(context.getResources().getColor(android.R.color.white));
        snackbar.getView().setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    public static void showWelcomeSnake(final Context context, View view, String name, String familyName) {
        Snackbar snackbar = Snackbar.make(view, name + " " + familyName + " خوش آمدید", Snackbar.LENGTH_LONG)
                .setActionTextColor(context.getResources().getColor(android.R.color.white));
        snackbar.getView().setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    public static void showSnakeTurnOnGps(final Context context, View view) {
        Snackbar snackbar = Snackbar.make(view, context.getString(R.string.pleaseTurnOnDeviceGps), Snackbar.LENGTH_INDEFINITE)
                .setAction(context.getString(R.string.Setting), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                }).setActionTextColor(context.getResources().getColor(android.R.color.black)).setActionTextColor(context.getResources().getColor(android.R.color.white));
        snackbar.getView().setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    public static void showSnakeClickAgainForExit(final Context context, View view) {
        Snackbar snackbar = Snackbar.make(view, context.getString(R.string.PleaseBackButtonAgainForExit), Snackbar.LENGTH_INDEFINITE).setActionTextColor(context.getResources().getColor(android.R.color.white));
        snackbar.getView().setBackgroundColor(context.getResources().getColor(android.R.color.black));
        snackbar.setDuration(2000);
        snackbar.show();
    }

}
