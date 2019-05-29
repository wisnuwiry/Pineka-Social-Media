package com.wisnusaputra.pinekaapp.Quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wisnusaputra.pinekaapp.Activity.MainActivity;
import com.wisnusaputra.pinekaapp.R;

public class FinishQuizActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_QUIZ = 1;
    private TextView txt_highscore,txt_score,bintang;
    private int highscore,score,tsoal,soal;
    private RatingBar rating;
    Button btnHome;

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_quiz);

        txt_highscore =findViewById(R.id.txt_hightscore);
        txt_score =findViewById(R.id.score);
        bintang =findViewById(R.id.bintang);
        rating =findViewById(R.id.rating);
        img = findViewById(R.id.img);
        btnHome = findViewById(R.id.btnHome);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinishQuizActivity.this, HomeQuizActivity.class));
                finish();
            }
        });

        score =getIntent().getIntExtra("SCORE",0);
        txt_score.setText(score + "");

        SharedPreferences setting = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        highscore = setting.getInt("HIGH_SCORE",0);

        if (score > highscore){
            txt_highscore.setText("HS: "+ score);

//            save
            SharedPreferences.Editor editor = setting.edit();
            editor.putInt("HIGH_SCORE",score);
            editor.commit();
        }else {
            txt_highscore.setText("HS: "+ highscore);
        }

//        bintang
        Intent intentScore = getIntent();
        Bundle b = intentScore.getExtras();

        if (b!= null) {
            soal = (int) b.get("soal");
            tsoal = (int) b.get("tsoal");

            float good = (int) (tsoal / 0.8f);
            float medium = tsoal / 2;
            float poor = tsoal / 3;

            if (score == tsoal) {
                bintang.setText("perfect bintang 5");
                img.setImageResource(R.drawable.e);
                rating.setRating(5);
            } else if (score >= good) {
                bintang.setText("perfect bintang 4.5");
                img.setImageResource(R.drawable.d);
                rating.setRating(4.5f);
            } else if (score <= good && score >= medium) {
                bintang.setText("good bintang 2");
                img.setImageResource(R.drawable.c);
                rating.setRating(3);
            } else if (score <= medium && score >= poor) {
                bintang.setText("poor bintang 3");
                img.setImageResource(R.drawable.b);
                rating.setRating(2.5f);
            } else {
                bintang.setText("bad bintang 4");
                img.setImageResource(R.drawable.a);
                rating.setRating(0);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent home = new Intent(this, HomeQuizActivity.class);
        home.putExtra("HS",highscore);
        startActivity(home);
        finish();
    }
}
