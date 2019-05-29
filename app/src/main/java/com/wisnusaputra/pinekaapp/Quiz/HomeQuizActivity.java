package com.wisnusaputra.pinekaapp.Quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.wisnusaputra.pinekaapp.Activity.MainActivity;
import com.wisnusaputra.pinekaapp.R;

import java.util.List;

public class HomeQuizActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_QUIZ = 1;
    public static final String EXTRA_CATEGORY_ID = "extraCategoryID";
    public static final String EXTRA_CATEGORY_NAME = "extraCategoryName";
    public static final String EXTRA_DIFFICULTY = "extraDifficulty";

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";

    private TextView textViewHighscore;
    private Spinner spinnerDifficulty;
    ToggleButton pknBtn, budayaBtn, sejarahBtn, bhinekaBtn;

    private int highscore,score;
    private Boolean clicked = false;
    private int categoryID;
    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_quiz);

        textViewHighscore = findViewById(R.id.text_view_highscore);
//        spinnerCategory = findViewById(R.id.spinner_category);
        spinnerDifficulty = findViewById(R.id.spinner_difficulty);

        score =getIntent().getIntExtra("SCORE",0);
        textViewHighscore.setText(score + "");

        SharedPreferences setting = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        highscore = setting.getInt("HIGH_SCORE",0);

        if (score > highscore){
            textViewHighscore.setText("High Score: "+ score);

//            save
            SharedPreferences.Editor editor = setting.edit();
            editor.putInt("GAME_DATA",score);
            editor.commit();
        }else {
            textViewHighscore.setText("HS: "+ highscore);
        }

        pknBtn =findViewById(R.id.pknBtn);
        budayaBtn =findViewById(R.id.budayaBtn);
        sejarahBtn =findViewById(R.id.sejarahBtn);
        bhinekaBtn =findViewById(R.id.bhinekaBtn);

        pknBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    bhinekaBtn.setChecked(false);
                    budayaBtn.setChecked(false);
                    sejarahBtn.setChecked(false);

                    categoryID = 1;
                    categoryName = "Pkn";
            }
        });

        bhinekaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    pknBtn.setChecked(false);
                    budayaBtn.setChecked(false);
                    sejarahBtn.setChecked(false);

                    categoryID = 2;
                    categoryName = "Bhineka";
            }
        });
        sejarahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    bhinekaBtn.setChecked(false);
                    budayaBtn.setChecked(false);
                    pknBtn.setChecked(false);

                    categoryID = 3;
                    categoryName = "Sejarah";

            }
        });

        budayaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    bhinekaBtn.setChecked(false);
                    pknBtn.setChecked(false);
                    sejarahBtn.setChecked(false);

                    categoryID = 4;
                    categoryName = "Budaya";

            }
        });

        loadDifficultyLevels();

        Button buttonStartQuiz = findViewById(R.id.button_start_quiz);
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryID == 0) {
                    Toast.makeText(HomeQuizActivity.this, "Please Selsct Category", Toast.LENGTH_SHORT).show();
                } else if (categoryName == null || categoryName == "") {
                    Toast.makeText(HomeQuizActivity.this, "Please Selsct Category", Toast.LENGTH_SHORT).show();
                }else {
                    startQuiz();
                }
            }
        });
    }

    private void startQuiz() {
        String difficulty = spinnerDifficulty.getSelectedItem().toString();

        Intent intent = new Intent(HomeQuizActivity.this, QuizActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryID);
        intent.putExtra(EXTRA_CATEGORY_NAME, categoryName);
        intent.putExtra(EXTRA_DIFFICULTY, difficulty);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }


    private void loadDifficultyLevels() {
        String[] difficultyLevels = Question.getAllDifficultyLevels();

        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, difficultyLevels);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}

