package com.example.lodka.lodkanadejeapp;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class Settings_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Context context = null;
    public static int permissionCheck = 1;
    SwitchCompat swt;
    Boolean checker = false;
    Spinner themes;
    Boolean checker2 = false;
    private boolean isCreated = false;
    NavigationView navigationView;
    SharedPreferences themeInfo ;
    String themeSetting;
    LinearLayout navHeaderLogo;
    DrawerLayout drawer;
    TextView tw1;
    TextView tw2;
    TextView lblswitch;
    ProcessFunction processer;

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
            processer = new ProcessFunction();


            if (net != null && net.isConnected()) {
                if (processer.isOnline()) {
                    setContentView(R.layout.activity_settings);
                    navHeaderLogo = (LinearLayout)findViewById(R.id.nav_header_logo);
                    tw1 = (TextView) findViewById(R.id.textview1);
                    tw2 = (TextView) findViewById(R.id.textview2);
                    lblswitch = (TextView) findViewById(R.id.labelswitch);

                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

                    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                    drawer.setDrawerListener(toggle);
                    toggle.syncState();

                    navigationView = (NavigationView) findViewById(R.id.nav_view);
                    navigationView.setNavigationItemSelectedListener(this);
                    processer = new ProcessFunction();

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
                    processer.changeTheme(themes.getItemAtPosition(index).toString(), checker2, Settings_activity.this, navigationView, drawer);
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
        View header = navigationView.getHeaderView(0);
        LinearLayout linear = (LinearLayout) header.findViewById(R.id.nav_header_logo);

        navigationView.setItemIconTintList(ContextCompat.getColorStateList(Settings_activity.this, themeInfo.getInt("setItemIconTintList",R.drawable.menu_text_color_normal) ));
        navigationView.setItemTextColor(ContextCompat.getColorStateList(Settings_activity.this, themeInfo.getInt("setItemTextColor",R.drawable.menu_text_color_normal) ));
        navigationView.setBackgroundDrawable(ContextCompat.getDrawable(Settings_activity.this, themeInfo.getInt("setBackgroundDrawable",R.drawable.border_top_bottom_normal) ));
        sett.setTextColor(ContextCompat.getColorStateList(Settings_activity.this, themeInfo.getInt("settSetTextColor",R.color.colorWhite) ));
        text.setTextColor(ContextCompat.getColorStateList(Settings_activity.this, themeInfo.getInt("textSetTextColor",R.color.colorDefault) ));
        toolbar.setBackground(ContextCompat.getDrawable(Settings_activity.this, themeInfo.getInt("toolbarSetBackground",R.color.colorPrimary) ));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(themeInfo.getInt("toolbarGetNavigationIcon()",R.color.colorWhite)), PorterDuff.Mode.SRC_ATOP);
        linear.setBackground(ContextCompat.getDrawable(Settings_activity.this, themeInfo.getInt("linearSetBackground",R.drawable.border_top_bottom_normal_logo) ));
        drawer.setBackgroundColor(themeInfo.getInt("drawersetBackground", Color.WHITE));
        tw1.setTextColor(themeInfo.getInt("tw1setTextColor", Color.BLACK));
        tw2.setTextColor(themeInfo.getInt("tw2setTextColor", Color.BLACK));
        lblswitch.setTextColor(themeInfo.getInt("lblswitchsetTextColor", Color.BLACK));


    }





    @Override
    protected void onResume(){
        super.onResume();

        if(this.isCreated == true){
            Log.e("onResume","true");
            this.isCreated = false;
        }else{
            Log.e("onResume","false");
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(permissions.length == 0){
            return;
        }
        boolean allPermissionsGranted = true;
        if(grantResults.length>0){
            for(int grantResult: grantResults){
                if(grantResult != PackageManager.PERMISSION_GRANTED){
                    allPermissionsGranted = false;
                    break;
                }
            }
        }
        if(!allPermissionsGranted) {
            boolean somePermissionsForeverDenied = false;
            for (String permission : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    checker = false;
                    swt.setChecked(false);
                } else {
                    if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                        checker = true;
                        swt.setChecked(true);
                    } else {
                        checker = false;
                        swt.setChecked(false);
                        somePermissionsForeverDenied = true;
                    }
                }
            }
            if (somePermissionsForeverDenied) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                alertDialogBuilder.setTitle("Zapnutie povolenia")
                        .setMessage("Zadali ste, že nechcete aby sa Vás aplikácia pýtala na povolenie. Je nutné " +
                                "zapnúť povolenie ručne v nastaveniach.")
                        .setPositiveButton("Nastavenia", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                                checker = true;
                                swt.setChecked(true);
                            }
                        })
                        .setNegativeButton("Zrušiť", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checker = false;
                                swt.setChecked(false);
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }
        }

    }





    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id4 = item.getItemId();
        processer = new ProcessFunction();
        processer.MenuProcessing(id4, this, processer);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}