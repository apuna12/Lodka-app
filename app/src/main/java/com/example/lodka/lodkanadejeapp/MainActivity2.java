package com.example.lodka.lodkanadejeapp;

import android.*;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static com.example.lodka.lodkanadejeapp.R.id.et1;
import static com.example.lodka.lodkanadejeapp.R.id.et2;

/**
 * Created by Tibor on 12.05.2017.
 */

public class MainActivity2 extends Activity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    EditText edt1;
    EditText edt2;

    ProgressDialog pdialog;
    Context context = null;
    String email;
    String sprava;
    Button clickButton;
    StringBuilder celyEmail = new StringBuilder();
    javax.mail.Session session;
    boolean checker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mail);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


            context = this;


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            pdialog = new ProgressDialog(MainActivity2.this);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            clickButton = (Button) findViewById(R.id.button1);
            clickButton.setOnClickListener(this);




        }
        catch (Exception e)
        {
            Log.e("mojachyba",e.getMessage());
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Button buttonSettings = (Button) toolbar.findViewById(R.id.button_settings);

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity2.this, Settings_activity.class);
                MainActivity2.this.startActivity(myIntent);

            }
        });


    }

    @Override
    public void onClick(View v) {

        edt1 = (EditText) findViewById(et1);
        edt2 = (EditText) findViewById(et2);
        email = edt1.getText().toString();
        sprava = edt2.getText().toString();



        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        if (isEmailValid(email) == false) {
            checker = false;
            Alert("Zadajte prosím správny email :)");
        }

        else if (sprava.isEmpty()) {
            checker = false;
            Alert("Zadajte prosím správu :)");
        }
        if(isEmailValid(email) && !sprava.isEmpty())
        {
            checker = true;
        }

        if (checker == true){
            celyEmail = new StringBuilder();
            celyEmail.append("Mail od: ");
            celyEmail.append(email);
            celyEmail.append('\n');
            celyEmail.append('\n');
            celyEmail.append("Správa: ");
            celyEmail.append(sprava);


            session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("aplikaciaozlodka@gmail.com", "Aplikacia");
                }
            });

            pdialog = ProgressDialog.show(context, "", "Posielam mail...", true);
            RetreiveFeedTask task = new RetreiveFeedTask();
            task.execute();
        }


    }

class  RetreiveFeedTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("aplikaciaozlodka@gmail.com"));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress("lodka.nadeje@gmail.com"));
                message.setSubject("Email z aplikacie");
                message.setText(celyEmail.toString());

                Transport.send(message);

            }catch (MessagingException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String result){
            pdialog.dismiss();
            edt1.setText("");
            edt2.setText("");
            Toast.makeText(getApplicationContext(),"E-mail odoslaný", Toast.LENGTH_LONG).show();
        }
    }

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


    public void Alert(String text){
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean isPermissionGranted() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id2 = item.getItemId();

        if (id2 == R.id.nav_domov) {
            Intent myIntent = new Intent(MainActivity2.this, MainActivity.class);
            myIntent.putExtra("website","http://lodkanadeje.maweb.eu/");
            MainActivity2.this.startActivity(myIntent);
        } else if (id2 == R.id.nav_gallery) {
            Intent myIntent = new Intent(MainActivity2.this, MainActivity.class);
            myIntent.putExtra("website","http://lodkanadeje.rajce.idnes.cz/");
            MainActivity2.this.startActivity(myIntent);
        } else if (id2 == R.id.nav_facebook) {
            SharingToSocialMedia("com.facebook.katana");
        } else if (id2 == R.id.nav_twitter) {
            SharingToSocialMedia("com.twitter.android");
        } else if (id2 == R.id.nav_mail){
            Intent myIntent = new Intent(MainActivity2.this, MainActivity2.class);
            MainActivity2.this.startActivity(myIntent);
        } else if (id2 == R.id.nav_map){
            if(!isPermissionGranted()) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Hups, niečo je zle :(")
                        .setMessage("Neudelili ste povolenie pre zisťovanie polohy. Vrátime vás na pôvodnu stránku.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent myIntent = new Intent(MainActivity2.this, MainActivity.class);
                                myIntent.putExtra("website", "http://lodkanadeje.maweb.eu/");
                                MainActivity2.this.startActivity(myIntent);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
            if(isPermissionGranted()) {
                Intent myIntent = new Intent(MainActivity2.this, MainActivity3.class);
                MainActivity2.this.startActivity(myIntent);
            }
        } else if (id2 == R.id.nav_instagram){
            SharingToSocialMedia("com.instagram.android");
        } else if (id2 == R.id.nav_snapchat){
            SharingToSocialMedia("com.snapchat.android");
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
    boolean twice;
    @Override
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

        Toast.makeText(MainActivity2.this, "Znova stlačte tlačidlo 'Späť' pre ukončenie aplikácie", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
            }
        }, 3000);

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
