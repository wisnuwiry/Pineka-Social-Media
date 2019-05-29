package com.wisnusaputra.pinekaapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ramotion.paperonboarding.PaperOnboardingEngine;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnChangeListener;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;
import com.wisnusaputra.pinekaapp.R;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_main_layout);

//        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
//        }

        if (restorePref()){
            startActivity(new Intent(this, StartActivity.class));
            finish();

        }

        PaperOnboardingEngine engine = new PaperOnboardingEngine(findViewById(R.id.onboardingRootView), getDataForOnboarding(), getApplicationContext());

        engine.setOnChangeListener(new PaperOnboardingOnChangeListener() {
            @Override
            public void onPageChanged(int oldElementIndex, int newElementIndex) {

            }
        });

        engine.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                // Probably here will be your exit action
                startActivity(new Intent(IntroActivity.this, StartActivity.class));
                finish();
            }
        });

        savePref();

    }


    // Just example data for Onboarding
    private ArrayList<PaperOnboardingPage> getDataForOnboarding() {
        // prepare data
        PaperOnboardingPage scr1 = new PaperOnboardingPage("Culture", "All hotels and hostels are sorted by hospitality rating",
                Color.parseColor("#46A7F9"), R.drawable.culture, R.drawable.logo_pineka);
        PaperOnboardingPage scr2 = new PaperOnboardingPage("Destination", "We carefully verify all banks before add them into the app",
                Color.parseColor("#ED3F5B"), R.drawable.destination, R.drawable.logo_pineka);
        PaperOnboardingPage scr3 = new PaperOnboardingPage("Food & Drink", "All local stores are categorized for your convenience",
                Color.parseColor("#FDC730"), R.drawable.food, R.drawable.logo_pineka);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        return elements;
    }

    private boolean restorePref() {
        SharedPreferences pref= getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        Boolean isbeforeIntro = pref.getBoolean("introOPen", false);
        return isbeforeIntro;
    }

    private void savePref(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("introOPen", true);
        editor.commit();
    }

}
