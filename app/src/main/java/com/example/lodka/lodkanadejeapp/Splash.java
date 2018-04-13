package com.example.lodka.lodkanadejeapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    Thread splashThread;
    public static final int permissionCheck = 1;
    @Override
    public void onCreate(Bundle savedInstanceState){

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    permissionCheck);

        }
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            setContentView(R.layout.activity_splash);
            setSizeLogo();
            StartAnimations();
        }




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case permissionCheck: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setContentView(R.layout.activity_splash);
                    setSizeLogo();
                    StartAnimations();
                } else {
                    setContentView(R.layout.activity_splash);
                    setSizeLogo();
                    StartAnimations();
                }
            }
        }
    }

    private void StartAnimations(){
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.alpha);
        anim.reset();
        RelativeLayout l=(RelativeLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this,R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        TextView tv =(TextView) findViewById(R.id.madeby);
        tv.clearAnimation();
        iv.clearAnimation();
        iv.startAnimation(anim);
        tv.startAnimation(anim);

        splashThread = new Thread(){
            @Override
            public void run(){
                try{
                    int waited = 0;
                    while (waited<3500){
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splash.this.finish();
                } catch(InterruptedException e){

                } finally {
                    Splash.this.finish();
                }
            }
        };
        splashThread.start();
    }

    public void setSizeLogo(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        ImageView logo = (ImageView)findViewById(R.id.splash);
        RelativeLayout.LayoutParams params;
        if(metrics.widthPixels > metrics.heightPixels){
            params = new RelativeLayout.LayoutParams(Integer.valueOf(metrics.widthPixels/2) + Integer.valueOf(metrics.widthPixels/6), Integer.valueOf(metrics.widthPixels/4) + Integer.valueOf(metrics.widthPixels/12) );
            params.setMargins(50, Integer.valueOf(metrics.heightPixels/7), 50, 50);
        }else{
            params = new RelativeLayout.LayoutParams(Integer.valueOf(metrics.widthPixels),Integer.valueOf(metrics.widthPixels/2));
            params.setMargins(50, Integer.valueOf(metrics.heightPixels/4), 50, 50);
        }
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        logo.setLayoutParams(params);
    }
}
