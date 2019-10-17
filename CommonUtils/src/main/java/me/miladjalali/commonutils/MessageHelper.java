package me.miladjalali.commonutils;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

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

    public void TwoChooseSnackbar(Context context, View view, String text, View.OnClickListener onClickListenerPositive, View.OnClickListener onClickListenerNegative)
    {
        // Create the Snackbar
        LinearLayout.LayoutParams objLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setPadding(0,0,0,0);
        // Hide the text
        TextView textView = layout.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
        // Inflate our custom view
        View snackView = mInflater.inflate(R.layout.two_choice_snackbar, null);
        // Configure the view
        TextView txtPositive = snackView.findViewById(R.id.txtPositive);
        txtPositive.setOnClickListener(onClickListenerPositive);

        TextView txtNegative = snackView.findViewById(R.id.txtNegative);
        txtNegative.setOnClickListener(onClickListenerNegative);

        // Add the view to the Snackbar's layout
        layout.addView(snackView, objLayoutParams);
        // Show the Snackbar
        snackbar.show();
    }

}
