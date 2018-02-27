package com.example.lodka.lodkanadejeapp;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    WebView wv;
    ProgressBar progressBar;
    Context context = null;
    private GoogleMap mMap;
    final String TAG = this.getClass().getName();
    public static int permissionCheck = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    permissionCheck);

        }

            try {
                ConnectivityManager con = (ConnectivityManager) getSystemService(context.CONNECTIVITY_SERVICE);
                NetworkInfo net = con.getActiveNetworkInfo();
                String web;
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                super.onCreate(savedInstanceState);
                if (net != null && net.isConnected()) {
                    if (isOnline()) {



                        setContentView(R.layout.activity_main);
                        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                        drawer.setDrawerListener(toggle);
                        toggle.syncState();

                        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                        navigationView.setNavigationItemSelectedListener(this);

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


                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Hups, niečo je zle :(")
                            .setMessage("Chýba pripojenie k internetu. Zapnite prosím dáta alebo Wi-Fi a spustite aplikáciu znova.")
                            //.setMessage("Chýba pripojenie k internetu. Aplikácia je v offline režime")
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


        }


    public boolean isPermissionGranted() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                /*ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 1);*/
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }

    }



    public Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }



    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            progressBar.setVisibility(View.GONE);
        }
    }


    boolean twice;

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
        Log.d(TAG,"Click");

        if(twice == true){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }
        twice = true;
        Log.d(TAG,"twice" + twice);

        Toast.makeText(MainActivity.this, "Znova stlačte tlačidlo 'Späť' pre ukončenie aplikácie", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
                Log.d(TAG,"twice" + twice);
            }
        }, 3000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

        if (id == R.id.nav_domov) {
            wv = (WebView) findViewById(R.id.webb);
            progressBar.setVisibility(View.VISIBLE);
            wv.setWebViewClient(new WebViewClient() {

                public void onPageFinished(WebView view, String url) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
            wv.loadUrl("http://www.lodkanadeje.maweb.eu/");

        } else if (id == R.id.nav_gallery) {
            wv = (WebView) findViewById(R.id.webb);
            progressBar.setVisibility(View.VISIBLE);
            wv.setWebViewClient(new WebViewClient() {

                public void onPageFinished(WebView view, String url) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
            wv.loadUrl("http://lodkanadeje.rajce.idnes.cz/");
        } else if (id == R.id.nav_facebook) {
            SharingToSocialMedia("com.facebook.katana");
        } else if (id == R.id.nav_twitter) {
            SharingToSocialMedia("com.twitter.android");
        } else if (id == R.id.nav_mail){
            Intent myIntent = new Intent(MainActivity.this, MainActivity2.class);
            MainActivity.this.startActivity(myIntent);
        } else if (id == R.id.nav_map){
            if(!isPermissionGranted()){

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Hups, niečo je zle :(")
                        .setMessage("Neudelili ste povolenie pre zisťovanie polohy. Vrátime vás na pôvodnu stránku.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                wv = (WebView) findViewById(R.id.webb);
                                progressBar.setVisibility(View.VISIBLE);
                                wv.setWebViewClient(new WebViewClient() {

                                    public void onPageFinished(WebView view, String url) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                });
                                wv.loadUrl("http://www.lodkanadeje.maweb.eu/");
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
            if(isPermissionGranted()) {
                Intent myIntent = new Intent(MainActivity.this, MainActivity3.class);
                MainActivity.this.startActivity(myIntent);
            }
        } else if (id == R.id.nav_instagram){
            SharingToSocialMedia("com.instagram.android");
        } else if (id == R.id.nav_snapchat){
            SharingToSocialMedia("com.snapchat.android");
        } else if (id == R.id.button_settings){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

