package com.wisnusaputra.pinekaapp.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wisnusaputra.pinekaapp.R;

public class SplashActivity extends AppCompatActivity {

    TextView titlepage, subtitlepage;
    ImageView icstates;
    Animation btt, bttwo, imganim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        bttwo = AnimationUtils.loadAnimation(this, R.anim.bttwo);
        imganim = AnimationUtils.loadAnimation(this, R.anim.imganim);

        icstates = findViewById(R.id.icstates);
        titlepage = findViewById(R.id.titlepage);
        subtitlepage = findViewById(R.id.subtitlepage);

        icstates.startAnimation(imganim);
        titlepage.startAnimation(btt);
        subtitlepage.startAnimation(bttwo);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        },3200);
    }
}
