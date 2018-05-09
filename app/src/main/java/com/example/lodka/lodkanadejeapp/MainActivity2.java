package com.example.lodka.lodkanadejeapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Properties;
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
    NavigationView navigationView;
    String themeSetting;
    SharedPreferences themeInfo ;
    static Boolean checker2 = false;
    DrawerLayout drawer;
    TextView tw1;
    TextView tw2;
    ProcessFunction processer;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        themeInfo = getSharedPreferences("THEMECONFIG",0);
        themeSetting = themeInfo.getString("theme","Základná");
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mail);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            tw1 = (TextView) findViewById(R.id.tw1M);
            tw2 = (TextView) findViewById(R.id.tw2M);
            context = this;


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
            pdialog = new ProgressDialog(MainActivity2.this);
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            clickButton = (Button) findViewById(R.id.button1);
            clickButton.setOnClickListener(this);
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

        if(!themeSetting.equals(themeInfo)) {
            if (themeSetting.equals("Základná")) {
                setTheme(R.style.AppTheme);
                processer.changeTheme("Základná", checker2, MainActivity2.this, navigationView, drawer);
                checker2 = true;
            }
            if (themeSetting.equals("Matrix")) {
                setTheme(R.style.AppThemeMatrixDivider);
                processer.changeTheme("Matrix", checker2, MainActivity2.this, navigationView, drawer);
                checker2 = true;
            }
            if (themeSetting.equals("Gamers")) {
                setTheme(R.style.AppThemeGamersDivider);
                processer.changeTheme("Gamers", checker2, MainActivity2.this, navigationView, drawer);
                checker2 = true;
            }
        }
        Button sett = (Button)findViewById(R.id.button_settings);
        TextView text = (TextView)findViewById(R.id.textview);
        View header = navigationView.getHeaderView(0);
        LinearLayout linear = (LinearLayout) header.findViewById(R.id.nav_header_logo);

        navigationView.setItemIconTintList(ContextCompat.getColorStateList(MainActivity2.this, themeInfo.getInt("setItemIconTintList",R.drawable.menu_text_color_normal) ));
        navigationView.setItemTextColor(ContextCompat.getColorStateList(MainActivity2.this, themeInfo.getInt("setItemTextColor",R.drawable.menu_text_color_normal) ));
        navigationView.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity2.this, themeInfo.getInt("setBackgroundDrawable",R.drawable.border_top_bottom_normal) ));
        sett.setTextColor(ContextCompat.getColorStateList(MainActivity2.this, themeInfo.getInt("settSetTextColor",R.color.colorWhite) ));
        text.setTextColor(ContextCompat.getColorStateList(MainActivity2.this, themeInfo.getInt("textSetTextColor",R.color.colorDefault) ));
        toolbar.setBackground(ContextCompat.getDrawable(MainActivity2.this, themeInfo.getInt("toolbarSetBackground",R.color.colorPrimary) ));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(themeInfo.getInt("toolbarGetNavigationIcon()",R.color.colorWhite)), PorterDuff.Mode.SRC_ATOP);
        linear.setBackground(ContextCompat.getDrawable(MainActivity2.this, themeInfo.getInt("linearSetBackground",R.drawable.border_top_bottom_normal_logo) ));
        drawer.setBackgroundColor(themeInfo.getInt("drawersetBackground", Color.WHITE));
        tw1.setTextColor(themeInfo.getInt("tw1setTextColor", Color.BLACK));
        tw2.setTextColor(themeInfo.getInt("tw2setTextColor", Color.BLACK));
        fab.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity2.this, themeInfo.getInt("fabBackground",R.color.colorPrimary)));
    }

    @Override
    public void onClick(View v) {

        edt1 = (EditText) findViewById(et1);
        edt2 = (EditText) findViewById(et2);
        email = edt1.getText().toString();
        sprava = edt2.getText().toString();
        processer = new ProcessFunction();


        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        if (processer.isEmailValid(email) == false) {
            checker = false;
            processer.Alert("Zadajte prosím správny email :)", context);
        }

        else if (sprava.isEmpty()) {
            checker = false;
            processer.Alert("Zadajte prosím správu :)", context);
        }
        if(processer.isEmailValid(email) && !sprava.isEmpty())
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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id2 = item.getItemId();
        processer = new ProcessFunction();
        processer.MenuProcessing(id2, this, processer);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

}
