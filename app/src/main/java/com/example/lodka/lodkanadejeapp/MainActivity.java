package com.example.lodka.lodkanadejeapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    WebView wv;
    ProgressBar progressBar;
    Context context = null;
    NavigationView navigationView;
    SharedPreferences themeInfo ;
    String themeSetting;
    ProcessFunction processer;
    static Boolean checker = false;
    DrawerLayout drawer;
    boolean twice;
    FloatingActionButton fab;
    boolean isFABOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        themeInfo = getSharedPreferences("THEMECONFIG",0);
        themeSetting = themeInfo.getString("theme","Základná");
        processer = new ProcessFunction();
            try {
                ConnectivityManager con = (ConnectivityManager) getSystemService(context.CONNECTIVITY_SERVICE);
                NetworkInfo net = con.getActiveNetworkInfo();
                String web;
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                super.onCreate(savedInstanceState);
                if (net != null && net.isConnected()) {
                    if (processer.isOnline()) {



                        setContentView(R.layout.activity_main);
                        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);




                        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
                            @Override
                            public void onDrawerSlide(View drawerView, float slideOffset) {

                            }

                            @Override
                            public void onDrawerOpened(View drawerView) {
                                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_floating_button));

                            }

                            @Override
                            public void onDrawerClosed(View drawerView) {
                                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_floating_button));

                            }

                            @Override
                            public void onDrawerStateChanged(int newState) {

                            }
                        });
                        toggle.syncState();
                        navigationView = (NavigationView) findViewById(R.id.nav_view);
                        navigationView.setNavigationItemSelectedListener(this);
                        processer = new ProcessFunction();
                        fab = (FloatingActionButton) findViewById(R.id.fab);
                        MultiTouchListener touchListener=new MultiTouchListener(this);
                        fab.setOnTouchListener(touchListener);
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(!drawer.isDrawerVisible(GravityCompat.START))
                                {
                                    drawer.openDrawer(Gravity.LEFT);
                                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_floating_button));
                                }
                                else
                                {
                                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_floating_button));
                                }
                            }
                        });
                        if (getIntent().getStringExtra("website") == null) {
                            web = "http://www.lodkanadeje.maweb.eu/";
                        } else {
                            web = getIntent().getStringExtra("website");
                        }
                        wv = (WebView) findViewById(R.id.webb);
                        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
                        progressBar.getIndeterminateDrawable().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
                        progressBar.setScaleY(0.1f);
                        progressBar.setScaleX(0.1f);
                        progressBar.setVisibility(View.VISIBLE);
                        wv.getSettings().setJavaScriptEnabled(true);
                        wv.setWebViewClient(new WebViewClient() {

                            public void onPageFinished(WebView view, String url) {
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });

                        wv.loadUrl(web);

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

                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

                    Button buttonSettings = (Button) toolbar.findViewById(R.id.button_settings);
                    buttonSettings.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent myIntent = new Intent(MainActivity.this, Settings_activity.class);
                            MainActivity.this.startActivity(myIntent);

                        }
                    });
                    
                    if(!themeSetting.equals(themeInfo)) {
                        if (themeSetting.equals("Základná")) {
                            setTheme(R.style.AppTheme);
                            processer.changeTheme("Základná", checker, MainActivity.this, navigationView, drawer);
                            checker = true;
                        }
                        if (themeSetting.equals("Matrix")) {
                            setTheme(R.style.AppThemeMatrixDivider);
                            processer.changeTheme("Matrix", checker, MainActivity.this, navigationView, drawer);
                            checker = true;
                        }
                        if (themeSetting.equals("Gamers")) {
                            setTheme(R.style.AppThemeGamersDivider);
                            processer.changeTheme("Gamers", checker, MainActivity.this, navigationView, drawer);
                            checker = true;
                        }
                    }

                } else
                {
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Button sett = (Button)findViewById(R.id.button_settings);
        TextView text = (TextView)findViewById(R.id.textview);
        navigationView.setItemIconTintList(ContextCompat.getColorStateList(MainActivity.this, themeInfo.getInt("setItemIconTintList",R.drawable.menu_text_color_normal) ));
        navigationView.setItemTextColor(ContextCompat.getColorStateList(MainActivity.this, themeInfo.getInt("setItemTextColor",R.drawable.menu_text_color_normal) ));
        navigationView.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, themeInfo.getInt("setBackgroundDrawable",R.drawable.border_top_bottom_normal) ));
        sett.setTextColor(ContextCompat.getColorStateList(MainActivity.this, themeInfo.getInt("settSetTextColor",R.color.colorWhite) ));
        text.setTextColor(ContextCompat.getColorStateList(MainActivity.this, themeInfo.getInt("textSetTextColor",R.color.colorDefault) ));
        toolbar.setBackground(ContextCompat.getDrawable(MainActivity.this, themeInfo.getInt("toolbarSetBackground",R.color.colorPrimary) ));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(themeInfo.getInt("toolbarGetNavigationIcon()",R.color.colorWhite)), PorterDuff.Mode.SRC_ATOP);
        fab.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, themeInfo.getInt("fabBackground",R.color.colorPrimary)));
        }

    private void showFABMenu(){
        isFABOpen=true;
        fab.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fab.animate().translationY(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (wv.canGoBack()) {
                        wv.goBack();
                    } else {
                        onBackPressed();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }


    public void onBackPressed() {

        if(twice == true){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }
        twice = true;
        Toast.makeText(MainActivity.this, "Znova stlačte tlačidlo 'Späť' pre ukončenie aplikácie", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
            }
        }, 3000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.button_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        processer = new ProcessFunction();
        processer.MenuProcessing(id, this, processer);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}

