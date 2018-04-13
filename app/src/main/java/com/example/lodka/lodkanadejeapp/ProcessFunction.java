package com.example.lodka.lodkanadejeapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by tibor.kocik on 13-Apr-18.
 */

public class ProcessFunction {

    public void changeTheme(String str, Boolean checker, Activity context)
    {
        LinearLayout navHeader = (LinearLayout) findViewById(R.id.nav_header);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Button sett = (Button) findViewById(R.id.button_settings);
        TextView text = (TextView) findViewById(R.id.textview);
        SharedPreferences themeInfo = getSharedPreferences("THEMECONFIG",0);
        SharedPreferences.Editor editor = themeInfo.edit();
        View header = navigationView.getHeaderView(0);
        LinearLayout linear = (LinearLayout) header.findViewById(R.id.nav_header_logo);


        if (str.equals("Základná")) {
            navigationView.setItemIconTintList(ContextCompat.getColorStateList(context, R.drawable.menu_text_color_normal));
            navigationView.setItemTextColor(ContextCompat.getColorStateList(context, R.drawable.menu_text_color_normal));
            navigationView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_normal));
            navHeader.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_normal));
            sett.setTextColor(ContextCompat.getColorStateList(context, R.color.colorWhite));
            text.setTextColor(ContextCompat.getColorStateList(context, R.color.colorDefault));
            toolbar.setBackground(ContextCompat.getDrawable(context, R.color.colorPrimary));
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
            linear.setBackground(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_normal_logo));
            if(context instanceof Settings_activity || context instanceof MainActivity2)
            {
                drawer.setBackgroundColor(Color.WHITE);
                tw1.setTextColor(Color.BLACK);
                tw2.setTextColor(Color.BLACK);
                if(context instanceof Settings_activity)
                {
                    lblswitch.setTextColor(Color.BLACK);
                }
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
            navHeader.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_matrix));
            sett.setTextColor(ContextCompat.getColorStateList(context, R.drawable.menu_text_color_matrix));
            text.setTextColor(ContextCompat.getColorStateList(context, R.drawable.menu_text_color_matrix));
            toolbar.setBackground(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_matrix));
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.drawable.menu_text_color_matrix), PorterDuff.Mode.SRC_ATOP);
            linear.setBackground(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_matrix_logo));
            if(context instanceof Settings_activity || context instanceof MainActivity2)
            {
                drawer.setBackgroundColor(Color.BLACK);
                tw1.setTextColor(Color.GREEN);
                tw2.setTextColor(Color.GREEN);
                if(context instanceof Settings_activity)
                {
                    lblswitch.setTextColor(Color.GREEN);
                }
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
                    Utils.changeToTheme(context, Utils.THEME_DEFAULT);
            }
            else
            {
                Utils.changeToTheme(context, Utils.THEME_DEFAULT);
            }
        }
        if (str.equals("Gamers")) {

            navigationView.setItemIconTintList(ContextCompat.getColorStateList(context, R.drawable.menu_text_color_gamers));
            navigationView.setItemTextColor(ContextCompat.getColorStateList(context, R.drawable.menu_text_color_gamers));
            navigationView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_gamers));
            navHeader.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_gamers));
            sett.setTextColor(ContextCompat.getColorStateList(context, R.drawable.menu_text_color_gamers));
            text.setTextColor(ContextCompat.getColorStateList(context, R.drawable.menu_text_color_gamers));
            toolbar.setBackground(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_gamers));
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.drawable.menu_text_color_gamers), PorterDuff.Mode.SRC_ATOP);
            linear.setBackground(ContextCompat.getDrawable(context, R.drawable.border_top_bottom_gamers_logo));
            if(context instanceof Settings_activity || context instanceof MainActivity2)
            {
                drawer.setBackgroundColor(Color.BLACK);
                tw1.setTextColor(Color.RED);
                tw2.setTextColor(Color.RED);
                if(context instanceof Settings_activity)
                {
                    lblswitch.setTextColor(Color.RED);
                }
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
                    Utils.changeToTheme(context, Utils.THEME_DEFAULT);
            }
            else
            {
                Utils.changeToTheme(context, Utils.THEME_DEFAULT);
            }
        }
    }
}
