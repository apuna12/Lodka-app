package com.example.lodka.lodkanadejeapp;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v4.media.RatingCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    Thread splashThread;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setSizeLogo();
        StartAnimations();
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
