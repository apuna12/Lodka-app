package com.example.lodka.lodkanadejeapp;
import android.*;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;

import org.w3c.dom.Text;

import java.io.File;

public class Settings_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    WebView wv;
    ProgressBar progressBar;
    Context context = null;
    private GoogleMap mMap;
    final String TAG = this.getClass().getName();
    public static int permissionCheck = 1;
    SwitchCompat swt;
    Boolean checker = false;
    Spinner themes;
    Boolean checker2 = false;
    private boolean isCreated = false;
    NavigationView navigationView;
    SharedPreferences themeInfo ;
    SharedPreferences.Editor editor;
    String themeSetting;
    LinearLayout navHeaderLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            ConnectivityManager con = (ConnectivityManager) getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo net = con.getActiveNetworkInfo();
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            super.onCreate(savedInstanceState);

            themeInfo = getSharedPreferences("THEMECONFIG",0);
            themeSetting = themeInfo.getString("theme","Základná");



            if (net != null && net.isConnected()) {
                if (isOnline()) {
                    //setContentView(R.id.nav_header_logo);
                    navHeaderLogo = (LinearLayout)this.findViewById(R.id.nav_header_logo);
                    setContentView(R.layout.activity_settings);
                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                    drawer.setDrawerListener(toggle);
                    toggle.syncState();


                    navigationView.setNavigationItemSelectedListener(this);


                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Hups, niečo je zle :(")
                            .setMessage("Internet nie je dostupný")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    System.exit(0);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }

            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Hups, niečo je zle :(")
                        .setMessage("Chýba pripojenie k internetu. Zapnite prosím dáta alebo Wi-Fi a spustite aplikáciu znova.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        } catch (Exception e) {
            Log.e("chyba", e.getMessage());
        }

        swt = (SwitchCompat)findViewById(R.id.lokalizationSwitch);

        if(ContextCompat.checkSelfPermission(Settings_activity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            swt.setChecked(false);
            checker = false;
        }
        else
        {
            swt.setChecked(true);
            checker = true;
        }
        swt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                MainActivity.checker = false;
                MainActivity2.checker2 = false;
                MainActivity3.checker3 = false;


                if(isChecked == true)
                {
                    if (ContextCompat.checkSelfPermission(Settings_activity.this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(Settings_activity.this,
                                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                permissionCheck);
                        checker = true;


                        }
                }
                else if (isChecked == false && checker == true)
                {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);

                    checker = false;
                }

            }

        });
        this.isCreated = true;
        themes = (Spinner) findViewById(R.id.comboBoxTheme);
        themeSetting = themeInfo.getString("theme","Základná");
        if(themeSetting.equals("Základná"))
        {
            themes.setSelection(0);
            setTheme(R.style.AppTheme);
            checker2 = false;
        }
        if(themeSetting.equals("Matrix"))
        {
            themes.setSelection(1);
            setTheme(R.style.AppThemeMatrixDivider);
            checker2 = false;
        }
        if(themeSetting.equals("Gamers"))
        {
            themes.setSelection(2);
            setTheme(R.style.AppThemeGamersDivider);
            checker2 = false;
        }
        themes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                int index = arg0.getSelectedItemPosition();
                if(!themeSetting.equals(themes.getItemAtPosition(index).toString()))
                {
                    changeTheme(themes.getItemAtPosition(index).toString());
                    checker2 = false;

                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                checker2 = true;
            }

        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Button sett = (Button)findViewById(R.id.button_settings);
        TextView text = (TextView)findViewById(R.id.textview);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(ContextCompat.getColorStateList(Settings_activity.this, themeInfo.getInt("setItemIconTintList",R.drawable.menu_text_color_normal) ));
        navigationView.setItemTextColor(ContextCompat.getColorStateList(Settings_activity.this, themeInfo.getInt("setItemTextColor",R.drawable.menu_text_color_normal) ));
        navigationView.setBackgroundDrawable(ContextCompat.getDrawable(Settings_activity.this, themeInfo.getInt("setBackgroundDrawable",R.drawable.border_top_bottom_normal) ));
        sett.setTextColor(ContextCompat.getColorStateList(Settings_activity.this, themeInfo.getInt("settSetTextColor",R.color.colorWhite) ));
        text.setTextColor(ContextCompat.getColorStateList(Settings_activity.this, themeInfo.getInt("textSetTextColor",R.color.colorDefault) ));
        toolbar.setBackground(ContextCompat.getDrawable(Settings_activity.this, themeInfo.getInt("toolbarSetBackground",R.color.colorPrimary) ));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(themeInfo.getInt("toolbarGetNavigationIcon()",R.color.colorWhite)), PorterDuff.Mode.SRC_ATOP);



    }


    public void changeTheme(String str)
    {

        LinearLayout navHeader = (LinearLayout) findViewById(R.id.nav_header);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Button sett = (Button)findViewById(R.id.button_settings);
        TextView text = (TextView)findViewById(R.id.textview);
        SharedPreferences themeInfo = getSharedPreferences("THEMECONFIG",0);
        SharedPreferences.Editor editor = themeInfo.edit();

        if(str.equals("Základná"))
        {

            navigationView.setItemIconTintList(ContextCompat.getColorStateList(Settings_activity.this, R.drawable.menu_text_color_normal));
            navigationView.setItemTextColor(ContextCompat.getColorStateList(Settings_activity.this, R.drawable.menu_text_color_normal));
            navigationView.setBackgroundDrawable(ContextCompat.getDrawable(Settings_activity.this, R.drawable.border_top_bottom_normal));
            navHeader.setBackgroundDrawable(ContextCompat.getDrawable(Settings_activity.this, R.drawable.border_top_bottom_normal));
            sett.setTextColor(ContextCompat.getColorStateList(Settings_activity.this, R.color.colorWhite));
            text.setTextColor(ContextCompat.getColorStateList(Settings_activity.this, R.color.colorDefault));
            toolbar.setBackground(ContextCompat.getDrawable(Settings_activity.this, R.color.colorPrimary));
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
            editor.putString("theme","Základná");
            editor.putInt("setItemIconTintList",R.drawable.menu_text_color_normal);
            editor.putInt("setItemTextColor", R.drawable.menu_text_color_normal);
            editor.putInt("setBackgroundDrawable", R.drawable.border_top_bottom_normal);
            editor.putInt("navHeaderSetBackgroundDrawable", R.drawable.border_top_bottom_normal);
            editor.putInt("settSetTextColor", R.color.colorWhite);
            editor.putInt("textSetTextColor", R.color.colorDefault);
            editor.putInt("toolbarSetBackground", R.color.colorPrimary);
            editor.putInt("toolbarGetNavigationIcon()", R.color.colorWhite);
            editor.commit();
            Utils.changeToTheme(Settings_activity.this, Utils.THEME_DEFAULT);

        }
        if (str.equals("Matrix"))
        {

            navigationView.setItemIconTintList(ContextCompat.getColorStateList(Settings_activity.this, R.drawable.menu_text_color_matrix));
            navigationView.setItemTextColor(ContextCompat.getColorStateList(Settings_activity.this, R.drawable.menu_text_color_matrix));
            navigationView.setBackgroundDrawable(ContextCompat.getDrawable(Settings_activity.this, R.drawable.border_top_bottom_matrix));
            navHeader.setBackgroundDrawable(ContextCompat.getDrawable(Settings_activity.this, R.drawable.border_top_bottom_matrix));
            sett.setTextColor(ContextCompat.getColorStateList(Settings_activity.this, R.drawable.menu_text_color_matrix));
            text.setTextColor(ContextCompat.getColorStateList(Settings_activity.this, R.drawable.menu_text_color_matrix));
            toolbar.setBackground(ContextCompat.getDrawable(Settings_activity.this, R.drawable.border_top_bottom_matrix));
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.drawable.menu_text_color_matrix), PorterDuff.Mode.SRC_ATOP);
            editor.putString("theme","Matrix");
            editor.putInt("setItemIconTintList",R.drawable.menu_text_color_matrix);
            editor.putInt("setItemTextColor", R.drawable.menu_text_color_matrix);
            editor.putInt("setBackgroundDrawable", R.drawable.border_top_bottom_matrix);
            editor.putInt("navHeaderSetBackgroundDrawable", R.drawable.border_top_bottom_matrix);
            editor.putInt("settSetTextColor", R.drawable.menu_text_color_matrix);
            editor.putInt("textSetTextColor", R.drawable.menu_text_color_matrix);
            editor.putInt("toolbarSetBackground", R.drawable.border_top_bottom_matrix);
            editor.putInt("toolbarGetNavigationIcon()", R.drawable.menu_text_color_matrix);
            editor.commit();
            Utils.changeToTheme(this, Utils.THEME_MATRIX);

        }
        if(str.equals("Gamers"))
        {

            navigationView.setItemIconTintList(ContextCompat.getColorStateList(Settings_activity.this, R.drawable.menu_text_color_gamers));
            navigationView.setItemTextColor(ContextCompat.getColorStateList(Settings_activity.this, R.drawable.menu_text_color_gamers));
            navigationView.setBackgroundDrawable(ContextCompat.getDrawable(Settings_activity.this, R.drawable.border_top_bottom_gamers));
            navHeader.setBackgroundDrawable(ContextCompat.getDrawable(Settings_activity.this, R.drawable.border_top_bottom_gamers));
            sett.setTextColor(ContextCompat.getColorStateList(Settings_activity.this, R.drawable.menu_text_color_gamers));
            text.setTextColor(ContextCompat.getColorStateList(Settings_activity.this, R.drawable.menu_text_color_gamers));
            toolbar.setBackground(ContextCompat.getDrawable(Settings_activity.this, R.drawable.border_top_bottom_gamers));
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.drawable.menu_text_color_gamers), PorterDuff.Mode.SRC_ATOP);

            editor.putString("theme","Gamers");
            editor.putInt("setItemIconTintList",R.drawable.menu_text_color_gamers);
            editor.putInt("setItemTextColor", R.drawable.menu_text_color_gamers);
            editor.putInt("setBackgroundDrawable", R.drawable.border_top_bottom_gamers);
            editor.putInt("navHeaderSetBackgroundDrawable", R.drawable.border_top_bottom_gamers);
            editor.putInt("settSetTextColor", R.drawable.menu_text_color_gamers);
            editor.putInt("textSetTextColor", R.drawable.menu_text_color_gamers);
            editor.putInt("toolbarSetBackground", R.drawable.border_top_bottom_gamers);
            editor.putInt("toolbarGetNavigationIcon()", R.drawable.menu_text_color_gamers);
            editor.commit();
            Utils.changeToTheme(this, Utils.THEME_GAMERS);

        }


    }



    @Override
    protected void onResume(){
        super.onResume();

        if(this.isCreated == true){

            this.isCreated = false;
        }else{
            this.afterResume();
        }


    }




    public void afterResume(){

        if (ContextCompat.checkSelfPermission(Settings_activity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && checker == false)
        {
            checker = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(Settings_activity.this);
            builder.setTitle("Hups, niečo je zle :(")
                    .setMessage("Zrušili ste okno, kde je možné zrušiť povolenie. Presmerujeme vás späť")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Zrušiť", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            swt.setChecked(true);
                            checker = true;
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else
            checker = true;
    }
    

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == permissionCheck) {
            if (permissions.length == 1 &&
                    permissions[0].equals(android.Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == 0)
            {
                checker = true;
                swt.setChecked(true);
            }
            else
            {
                checker = false;
                swt.setChecked(false);
            }
        }
    }





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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id2 = item.getItemId();

        if (id2 == R.id.nav_domov) {
            Intent myIntent = new Intent(com.example.lodka.lodkanadejeapp.Settings_activity.this, MainActivity.class);
            myIntent.putExtra("website","http://lodkanadeje.maweb.eu/");
            com.example.lodka.lodkanadejeapp.Settings_activity.this.startActivity(myIntent);
        } else if (id2 == R.id.nav_gallery) {
            Intent myIntent = new Intent(com.example.lodka.lodkanadejeapp.Settings_activity.this, MainActivity.class);
            myIntent.putExtra("website","http://lodkanadeje.rajce.idnes.cz/");
            com.example.lodka.lodkanadejeapp.Settings_activity.this.startActivity(myIntent);
        } else if (id2 == R.id.nav_facebook) {
            SharingToSocialMedia("com.facebook.katana");
        } else if (id2 == R.id.nav_twitter) {
            SharingToSocialMedia("com.twitter.android");
        } else if (id2 == R.id.nav_mail){
            Intent myIntent = new Intent(com.example.lodka.lodkanadejeapp.Settings_activity.this, com.example.lodka.lodkanadejeapp.MainActivity2.class);
            com.example.lodka.lodkanadejeapp.Settings_activity.this.startActivity(myIntent);
        } else if (id2 == R.id.nav_instagram){
            SharingToSocialMedia("com.instagram.android");
        } else if (id2 == R.id.nav_snapchat){
            SharingToSocialMedia("com.snapchat.android");
        } else if (id2 == R.id.nav_map){
            if(!isPermissionGranted()) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Hups, niečo je zle :(")
                        .setMessage("Neudelili ste povolenie pre zisťovanie polohy. Vrátime vás na pôvodnu stránku.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent myIntent = new Intent(Settings_activity.this, MainActivity.class);
                                myIntent.putExtra("website", "http://lodkanadeje.maweb.eu/");
                                Settings_activity.this.startActivity(myIntent);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
            if(isPermissionGranted()) {
                Intent myIntent = new Intent(Settings_activity.this, MainActivity3.class);
                Settings_activity.this.startActivity(myIntent);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean isPermissionGranted() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }

    }

    public void SharingToSocialMedia(final String application) {


        if(application == "com.facebook.katana") {


            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "http://www.lodkanadeje.maweb.eu/");

            boolean installed = checkAppInstall(application);
            if (installed) {
                intent.setPackage(application);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(),
                        "Nie je nainštalovaná aplikácia", Toast.LENGTH_LONG).show();
            }
        }else if(application == "com.twitter.android"){


            Uri uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.mipmap.lodkauvod);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.putExtra(Intent.EXTRA_TEXT, "http://www.lodkanadeje.maweb.eu/");

            boolean installed = checkAppInstall(application);
            if (installed) {
                intent.setPackage(application);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(),
                        "Nie je nainštalovaná aplikácia", Toast.LENGTH_LONG).show();
            }
        }else if(application == "com.instagram.android"){

            final Uri uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.mipmap.lodkauvod2);

            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Pripomienka")
                    .setMessage("Pri zdieľaní nezabudnite prosím roztiahnúť obrázok na celú obrazovku")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.setType("image/png");
                            intent.putExtra(Intent.EXTRA_STREAM, uri);
                            boolean installed = checkAppInstall(application);


                            if (installed) {
                                intent.setPackage(application);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Nie je nainštalovaná aplikácia", Toast.LENGTH_LONG).show();
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
        }else if(application == "com.snapchat.android") {

            final Uri uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.mipmap.lodkauvod2);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            boolean installed = checkAppInstall(application);


            if (installed) {
                intent.setPackage(application);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(),
                        "Nie je nainštalovaná aplikácia", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean checkAppInstall(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }


}