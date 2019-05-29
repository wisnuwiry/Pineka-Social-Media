package com.wisnusaputra.pinekaapp.Quiz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.wisnusaputra.pinekaapp.Activity.OptionsActivity;
import com.wisnusaputra.pinekaapp.Activity.StartActivity;
import com.wisnusaputra.pinekaapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_IN_MILLIS = 30000;

    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_MILLIS_LEFT = "keyMillisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";

    private TextView textViewQuestion, textViewScore, textViewQuestionCount,textViewCountDown;
    private RadioGroup rbGroup;
    private RadioButton rb1, rb2;
    private Button buttonConfirmNext,skip,pause,finish;
    private ColorStateList textColorDefaultRb,textColorDefaultCd;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = COUNTDOWN_IN_MILLIS;
    private Boolean mTimerRunning,answered;

    private ArrayList<Question> questionList;
    private int questionCounter,questionCountTotal,score;
    private Question currentQuestion;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewQuestion = findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_score);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        textViewCountDown = findViewById(R.id.text_view_countdown);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);
        finish = findViewById(R.id.finish);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishQuiz();
            }
        });

        skip = findViewById(R.id.skip);
        pause = findViewById(R.id.pause);

        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultCd = textViewCountDown.getTextColors();

        Intent intent = getIntent();
        int categoryID = intent.getIntExtra(HomeQuizActivity.EXTRA_CATEGORY_ID, 0);
        String categoryName = intent.getStringExtra(HomeQuizActivity.EXTRA_CATEGORY_NAME);
        String difficulty = intent.getStringExtra(HomeQuizActivity.EXTRA_DIFFICULTY);


        if (savedInstanceState == null) {
            QuizDbHelper dbHelper = QuizDbHelper.getInstance(this);
            questionList = dbHelper.getQuestions(categoryID, difficulty);
            questionCountTotal = questionList.size();
            Collections.shuffle(questionList);

            showNextQuestion();
        } else {
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            questionCountTotal = questionList.size();
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion = questionList.get(questionCounter - 1);
            score = savedInstanceState.getInt(KEY_SCORE);
            timeLeftInMillis = savedInstanceState.getLong(KEY_MILLIS_LEFT);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);

            if (!answered) {
                startTimer();
            } else {
                updateCountDownText();
                showSolution();
            }
        }

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                int q = questionCountTotal - 1;
                if(q == questionCounter){
                    skip.setVisibility(View.GONE);
                }
                showNextQuestion();
                countDownTimer.start();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning){
                    pauseTimer();
                }else{
                    startTimer();
                }
            }
        });

    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                pause.setText("RESUME");
                updateCountDownText();
                checkAnswer();
                finishQuiz();
            }
        }.start();

        mTimerRunning =true;
        pause.setText("Pause");
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        mTimerRunning = false;
        textViewQuestion.setVisibility(View.GONE);

        final Dialog dialogPause = new Dialog(QuizActivity.this);
        dialogPause.requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (dialogPause.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.transparent));
            dialogPause.getWindow().setBackgroundDrawable(colorDrawable);

        }
        dialogPause.setContentView(R.layout.dialog);
        dialogPause.setCancelable(false);
        dialogPause.show();

        //Since the dialog is show to user just pause the timer in background
        onPause();

        //Setting type faces
        TextView txt = dialogPause.findViewById(R.id.txt);
        Button buttonNext = dialogPause.findViewById(R.id.dialog_next);
        ImageView img_dialog = dialogPause.findViewById(R.id.img);

        txt.setText("Pause Your Quiz");
        buttonNext.setText("Resume");
        img_dialog.setImageResource(R.drawable.pause);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPause.dismiss();
                textViewQuestion.setVisibility(View.VISIBLE);
                startTimer();
            }
        });

    }

    private void showNextQuestion() {
        rb1.setBackground(getResources().getDrawable(R.drawable.radiobtn));
        rb2.setBackground(getResources().getDrawable(R.drawable.radiobtn));
        rbGroup.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Confirm");

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startTimer();
        } else {
            finishQuiz();
        }
    }


    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(textColorDefaultCd);
        }
    }

    private void checkAnswer() {
        answered = true;

        countDownTimer.cancel();

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        if (answerNr == currentQuestion.getAnswerNr()) {
            score++;
            textViewScore.setText("Score: " + score);
            goodAnswer();
        }

        showSolution();
    }

    private void showSolution() {
        switch (currentQuestion.getAnswerNr()) {
            case 1:
                rb1.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            case 2:
                rb2.setBackgroundColor(getResources().getColor(R.color.green));
                break;
        }

        if (questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Next");
        } else {
            buttonConfirmNext.setText("Finish");
        }
    }

    private void finishQuiz() {
        countDownTimer.cancel();
        Intent resultIntent = new Intent(getApplicationContext(),FinishQuizActivity.class);
        resultIntent.putExtra("SCORE",score);
        resultIntent.putExtra("soal",questionCounter);
        resultIntent.putExtra("tsoal",questionCountTotal);
        startActivity(resultIntent);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void goodAnswer() {
        textViewQuestion.setVisibility(View.GONE);

        final Dialog dialogCorrect = new Dialog(QuizActivity.this);
        dialogCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (dialogCorrect.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.transparent));
            dialogCorrect.getWindow().setBackgroundDrawable(colorDrawable);

        }
        dialogCorrect.setContentView(R.layout.dialog);
        dialogCorrect.setCancelable(false);
        dialogCorrect.show();

        //Since the dialog is show to user just pause the timer in background
        onPause();

        //Setting type faces
        TextView txt = dialogCorrect.findViewById(R.id.txt);
        Button buttonNext = dialogCorrect.findViewById(R.id.dialog_next);

        if (questionCounter < questionCountTotal) {
            buttonNext.setText("Next");
        } else {
            buttonNext.setText("Finish");
        }

        txt.setText("Kamu berhasil "+currentQuestion.getAnswerNr());
        //OnCLick listener to go next que
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNextQuestion();
                dialogCorrect.dismiss();
                textViewQuestion.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_QUESTION_COUNT, questionCounter);
        outState.putLong(KEY_MILLIS_LEFT, timeLeftInMillis);
        outState.putBoolean(KEY_ANSWERED, answered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST, questionList);
    }
}
