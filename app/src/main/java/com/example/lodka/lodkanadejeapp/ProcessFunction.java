package com.example.lodka.lodkanadejeapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tibor.kocik on 13-Apr-18.
 */

public class ProcessFunction {

    //region changeTheme
    public void changeTheme(String str, Boolean checker, Activity context, NavigationView navigationView, DrawerLayout drawer)
    {
        TextView tw1;
        TextView tw2;
        TextView lblswitch;
        FloatingActionButton fab = (FloatingActionButton)context.findViewById(R.id.fab);
        View nheader = navigationView.getHeaderView(0);
        LinearLayout navHeader = (LinearLayout) nheader.findViewById(R.id.nav_header);
        Toolbar toolbar = (Toolbar) context.findViewById(R.id.toolbar);
        Button sett = (Button) context.findViewById(R.id.button_settings);
        TextView text = (TextView) context.findViewById(R.id.textview);
        SharedPreferences themeInfo = context.getSharedPreferences("THEMECONFIG",0);
        SharedPreferences.Editor editor = themeInfo.edit();
        View header = navigationView.getHeaderView(0);
        LinearLayout linear = (LinearLayout) header.findViewById(R.id.nav_header_logo);
        tw1 = null;
        tw2 = null;

        if (str.equals("Základná")) {
            navigationView.setItemIconTintList(ContextCompat.getColorStateList(context, R.drawable.menu_text_color_normal));
            navigationView.setItemTextColor(ContextCompat.getColorStateList(context, R.drawable.menu_text_color_normal));
            navigationView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_normal));
            if(context instanceof MainActivity3 == false)
            {
                navHeader.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_normal));
            }
            sett.setTextColor(ContextCompat.getColorStateList(context, R.color.colorWhite));
            text.setTextColor(ContextCompat.getColorStateList(context, R.color.colorDefault));
            toolbar.setBackground(ContextCompat.getDrawable(context, R.color.colorPrimary));
            toolbar.getNavigationIcon().setColorFilter(context.getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
            linear.setBackground(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_normal_logo));
            fab.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
            if(context instanceof Settings_activity || context instanceof MainActivity2)
            {

                if(context instanceof MainActivity2)
                {
                    tw1 = (TextView) context.findViewById(R.id.tw1M);
                    tw2 = (TextView) context.findViewById(R.id.tw2M);
                }
                if(context instanceof Settings_activity)
                {
                    tw1 = (TextView) context.findViewById(R.id.textview1);
                    tw2 = (TextView) context.findViewById(R.id.textview2);
                    lblswitch = (TextView) context.findViewById(R.id.labelswitch);
                    lblswitch.setTextColor(Color.BLACK);
                }
                drawer.setBackgroundColor(Color.WHITE);
                tw1.setTextColor(Color.BLACK);
                tw2.setTextColor(Color.BLACK);
            }
            editor.putString("theme", "Základná");
            editor.putInt("setItemIconTintList", R.drawable.menu_text_color_normal);
            editor.putInt("setItemTextColor", R.drawable.menu_text_color_normal);
            editor.putInt("setBackgroundDrawable", R.drawable.border_top_bottom_normal);
            editor.putInt("navHeaderSetBackgroundDrawable", R.drawable.border_top_bottom_normal);
            editor.putInt("settSetTextColor", R.color.colorWhite);
            editor.putInt("textSetTextColor", R.color.colorDefault);
            editor.putInt("toolbarSetBackground", R.color.colorPrimary);
            editor.putInt("toolbarGetNavigationIcon()", R.color.colorWhite);
            editor.putInt("linearSetBackground", R.drawable.border_top_bottom_normal_logo);
            editor.putInt("fabBackground", R.color.colorPrimary);
            if(context instanceof Settings_activity || context instanceof MainActivity2)
            {
                editor.putInt("drawersetBackground", Color.WHITE);
                editor.putInt("tw1setTextColor", Color.BLACK);
                editor.putInt("tw2setTextColor", Color.BLACK);
                if(context instanceof Settings_activity)
                {
                    editor.putInt("lblswitchsetTextColor", Color.BLACK);
                }
            }
            editor.commit();
            if(context instanceof Settings_activity != true)
            {
                if (!checker)
                    Utils.changeToTheme(context, Utils.THEME_DEFAULT);
            }
            else
            {
                Utils.changeToTheme(context, Utils.THEME_DEFAULT);
            }
        }
        if (str.equals("Matrix")) {

            navigationView.setItemIconTintList(ContextCompat.getColorStateList(context, R.drawable.menu_text_color_matrix));
            navigationView.setItemTextColor(ContextCompat.getColorStateList(context, R.drawable.menu_text_color_matrix));
            navigationView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_matrix));
            if(context instanceof MainActivity3 == false)
            {
                navHeader.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_normal));
            }
            sett.setTextColor(ContextCompat.getColorStateList(context, R.drawable.menu_text_color_matrix));
            text.setTextColor(ContextCompat.getColorStateList(context, R.drawable.menu_text_color_matrix));
            toolbar.setBackground(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_matrix));
            toolbar.getNavigationIcon().setColorFilter(context.getResources().getColor(R.drawable.menu_text_color_matrix), PorterDuff.Mode.SRC_ATOP);
            linear.setBackground(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_matrix_logo));
            fab.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorMatrix)));
            if(context instanceof Settings_activity || context instanceof MainActivity2)
            {

                if(context instanceof MainActivity2)
                {
                    tw1 = (TextView) context.findViewById(R.id.tw1M);
                    tw2 = (TextView) context.findViewById(R.id.tw2M);
                }
                if(context instanceof Settings_activity)
                {
                    tw1 = (TextView) context.findViewById(R.id.textview1);
                    tw2 = (TextView) context.findViewById(R.id.textview2);
                    lblswitch = (TextView) context.findViewById(R.id.labelswitch);
                    lblswitch.setTextColor(Color.BLACK);
                }
                drawer.setBackgroundColor(Color.WHITE);
                tw1.setTextColor(Color.BLACK);
                tw2.setTextColor(Color.BLACK);
            }
            editor.putString("theme", "Matrix");
            editor.putInt("setItemIconTintList", R.drawable.menu_text_color_matrix);
            editor.putInt("setItemTextColor", R.drawable.menu_text_color_matrix);
            editor.putInt("setBackgroundDrawable", R.drawable.border_top_bottom_matrix);
            editor.putInt("navHeaderSetBackgroundDrawable", R.drawable.border_top_bottom_matrix);
            editor.putInt("settSetTextColor", R.drawable.menu_text_color_matrix);
            editor.putInt("textSetTextColor", R.drawable.menu_text_color_matrix);
            editor.putInt("toolbarSetBackground", R.drawable.border_top_bottom_matrix);
            editor.putInt("toolbarGetNavigationIcon()", R.drawable.menu_text_color_matrix);
            editor.putInt("linearSetBackground", R.drawable.border_top_bottom_matrix_logo);
            editor.putInt("fabBackground", R.color.colorMatrix);
            if(context instanceof Settings_activity || context instanceof MainActivity2)
            {
                editor.putInt("drawersetBackground", Color.BLACK);
                editor.putInt("tw1setTextColor", Color.GREEN);
                editor.putInt("tw2setTextColor", Color.GREEN);
                if(context instanceof Settings_activity)
                {
                    editor.putInt("lblswitchsetTextColor", Color.GREEN);
                }
            }
            editor.commit();
            if(context instanceof Settings_activity != true)
            {
                if (!checker)
                    Utils.changeToTheme(context, Utils.THEME_MATRIX);
            }
            else
            {
                Utils.changeToTheme(context, Utils.THEME_MATRIX);
            }
        }
        if (str.equals("Gamers")) {

            navigationView.setItemIconTintList(ContextCompat.getColorStateList(context, R.drawable.menu_text_color_gamers));
            navigationView.setItemTextColor(ContextCompat.getColorStateList(context, R.drawable.menu_text_color_gamers));
            navigationView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_gamers));
            if(context instanceof MainActivity3 == false)
            {
                navHeader.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_normal));
            }
            sett.setTextColor(ContextCompat.getColorStateList(context, R.drawable.menu_text_color_gamers));
            text.setTextColor(ContextCompat.getColorStateList(context, R.drawable.menu_text_color_gamers));
            toolbar.setBackground(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_gamers));
            toolbar.getNavigationIcon().setColorFilter(context.getResources().getColor(R.drawable.menu_text_color_gamers), PorterDuff.Mode.SRC_ATOP);
            linear.setBackground(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_gamers_logo));
            fab.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGamers)));
            if(context instanceof Settings_activity || context instanceof MainActivity2)
            {

                if(context instanceof MainActivity2)
                {
                    tw1 = (TextView) context.findViewById(R.id.tw1M);
                    tw2 = (TextView) context.findViewById(R.id.tw2M);
                }
                if(context instanceof Settings_activity)
                {
                    tw1 = (TextView) context.findViewById(R.id.textview1);
                    tw2 = (TextView) context.findViewById(R.id.textview2);
                    lblswitch = (TextView) context.findViewById(R.id.labelswitch);
                    lblswitch.setTextColor(Color.BLACK);
                }
                drawer.setBackgroundColor(Color.WHITE);
                tw1.setTextColor(Color.BLACK);
                tw2.setTextColor(Color.BLACK);
            }
            editor.putString("theme", "Gamers");
            editor.putInt("setItemIconTintList", R.drawable.menu_text_color_gamers);
            editor.putInt("setItemTextColor", R.drawable.menu_text_color_gamers);
            editor.putInt("setBackgroundDrawable", R.drawable.border_top_bottom_gamers);
            editor.putInt("navHeaderSetBackgroundDrawable", R.drawable.border_top_bottom_gamers);
            editor.putInt("settSetTextColor", R.drawable.menu_text_color_gamers);
            editor.putInt("textSetTextColor", R.drawable.menu_text_color_gamers);
            editor.putInt("toolbarSetBackground", R.drawable.border_top_bottom_gamers);
            editor.putInt("toolbarGetNavigationIcon()", R.drawable.menu_text_color_gamers);
            editor.putInt("linearSetBackground", R.drawable.border_top_bottom_gamers_logo);
            editor.putInt("fabBackground", R.color.colorGamers);
            if(context instanceof Settings_activity || context instanceof MainActivity2)
            {
                editor.putInt("drawersetBackground", Color.BLACK);
                editor.putInt("tw1setTextColor", Color.RED);
                editor.putInt("tw2setTextColor", Color.RED);
                if(context instanceof Settings_activity)
                {
                    editor.putInt("lblswitchsetTextColor", Color.RED);
                }
            }
            editor.commit();
            if(context instanceof Settings_activity != true)
            {
                if (!checker)
                    Utils.changeToTheme(context, Utils.THEME_GAMERS);
            }
            else
            {
                Utils.changeToTheme(context, Utils.THEME_GAMERS);
            }
        }
    }
//endregion

    //region SharingToSocialMedia
    public void SharingToSocialMedia(final String application, final Activity context) {

        if(application == "com.facebook.katana") {


            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "http://www.lodkanadeje.maweb.eu/");

            boolean installed = checkAppInstall(application, context);
            if (installed) {
                intent.setPackage(application);
                context.startActivity(intent);
            } else {

                Toast.makeText(context.getApplicationContext(),
                        "Nie je nainštalovaná aplikácia", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Hups, niečo je zle :(")
                        .setMessage("Aplikácia nie je nainštalovaná. Ak chcete aplikáciu nainštalovať kliknite na tlačidlo OK. V opačnom prípade kliknite na Zrušiť")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                final String appPackageName = "com.facebook.katana";
                                try {
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }else if(application == "com.twitter.android"){


            Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.mipmap.lodkauvod);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.putExtra(Intent.EXTRA_TEXT, "http://www.lodkanadeje.maweb.eu/");

            boolean installed = checkAppInstall(application, context);
            if (installed) {
                intent.setPackage(application);
                context.startActivity(intent);
            } else {
                Toast.makeText(context.getApplicationContext(),
                        "Nie je nainštalovaná aplikácia", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Hups, niečo je zle :(")
                        .setMessage("Aplikácia nie je nainštalovaná. Ak chcete aplikáciu nainštalovať kliknite na tlačidlo OK. V opačnom prípade kliknite na Zrušiť")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                final String appPackageName = "com.twitter.android";
                                try {
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }else if(application == "com.instagram.android"){

            final Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.mipmap.lodkauvod2);

            AlertDialog.Builder builder;
            AlertDialog.Builder builder2;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                builder2 = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(context);
                builder2 = new AlertDialog.Builder(context);
            }

            boolean installed = checkAppInstall(application, context);
            if (installed) {
                builder.setTitle("Pripomienka")
                        .setMessage("Pri zdieľaní nezabudnite prosím roztiahnúť obrázok na celú obrazovku")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {

                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_SEND);
                                intent.setType("image/png");
                                intent.putExtra(Intent.EXTRA_STREAM, uri);
                                intent.setPackage(application);
                                context.startActivity(intent);
                            }

                        }
                        )
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            } else {
                Toast.makeText(context.getApplicationContext(),
                        "Nie je nainštalovaná aplikácia", Toast.LENGTH_LONG).show();
                builder2.setTitle("Hups, niečo je zle :(")
                        .setMessage("Aplikácia nie je nainštalovaná. Ak chcete aplikáciu nainštalovať kliknite na tlačidlo OK. V opačnom prípade kliknite na Zrušiť")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final String appPackageName = "com.instagram.android";
                                try {
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                }
                            }
                        })

                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }



        }else if(application == "com.snapchat.android") {

            final Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.mipmap.lodkauvod2);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            boolean installed = checkAppInstall(application,context);


            if (installed) {
                intent.setPackage(application);
                context.startActivity(intent);
            } else {
                Toast.makeText(context.getApplicationContext(),
                        "Nie je nainštalovaná aplikácia", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Hups, niečo je zle :(")
                        .setMessage("Aplikácia nie je nainštalovaná. Ak chcete aplikáciu nainštalovať kliknite na tlačidlo OK. V opačnom prípade kliknite na Zrušiť")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                final String appPackageName = "com.snapchat.android";
                                try {
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
    }

    //endregion

    //region isPermissionGranted
    public boolean isPermissionGranted(Activity context) {

        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }

    }

    //endregion

    //region MenuProcessing
    public void MenuProcessing(int id, final Activity context, ProcessFunction processer)
    {
        if (id == R.id.nav_domov) {
            Intent myIntent = new Intent(context, MainActivity.class);
            myIntent.putExtra("website","http://lodkanadeje.maweb.eu/");
            context.startActivity(myIntent);

        } else if (id == R.id.nav_gallery) {
            Intent myIntent = new Intent(context, MainActivity.class);
            myIntent.putExtra("website","https://drive.google.com/open?id=117LgdghiKO1WSz09DoxYNOE7-eiEjf4I");
            context.startActivity(myIntent);
        } else if (id == R.id.nav_facebook) {
            processer.SharingToSocialMedia("com.facebook.katana", context);
        } else if (id == R.id.nav_twitter) {
            processer.SharingToSocialMedia("com.twitter.android", context);
        } else if (id == R.id.nav_mail){
            Intent myIntent = new Intent(context, MainActivity2.class);
            context.startActivity(myIntent);
        } else if (id == R.id.nav_map){
            if(!processer.isPermissionGranted(context)){

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Hups, niečo je zle :(")
                        .setMessage("Neudelili ste povolenie pre zisťovanie polohy. Vrátime vás na pôvodnu stránku. Pre zapnutie povolenia prejdite do nastavení aplikácie.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final Activity innerContext = context;
                                Intent myIntent = new Intent(innerContext, MainActivity.class);
                                myIntent.putExtra("website", "http://lodkanadeje.maweb.eu/");
                                innerContext.startActivity(myIntent);
                            }
                        })
                        .setNegativeButton("Nastavenia", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final Activity innerContext = context;
                                Intent myIntent = new Intent(innerContext, Settings_activity.class);
                                innerContext.startActivity(myIntent);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
            if(processer.isPermissionGranted(context)) {
                Intent myIntent = new Intent(context, MainActivity3.class);
                context.startActivity(myIntent);
            }
        } else if (id == R.id.nav_instagram){
            processer.SharingToSocialMedia("com.instagram.android", context);
        } else if (id == R.id.nav_snapchat){
            processer.SharingToSocialMedia("com.snapchat.android", context);
        }
    }
    //endregion

    //region isOnline
    public Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal == 0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    //endregion

    //region checkAppInstall
    private boolean checkAppInstall(String uri, Activity context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }
    //endregion

    //region Alert
    public void Alert(String text, Context context){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("Hups, niečo je zlé")
                .setMessage(text)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    //endregion

    //region isEmailValid
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    //endregion


}
