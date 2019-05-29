package com.wisnusaputra.pinekaapp.Quiz;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable  {
    public static final String LANGUAGE_ENG = "English";
    public static final String LANGUAGE_ID = "Indonesia";

    private int id;
    private String question;
    private String option1;
    private String option2;
    private int answerNr;
    private String difficulty;
    private int categoryID;

    public Question() {
    }

    public Question(String question, String option1, String option2,
                    int answerNr, String difficulty, int categoryID) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.answerNr = answerNr;
        this.difficulty = difficulty;
        this.categoryID = categoryID;
    }

    protected Question(Parcel in) {
        id = in.readInt();
        question = in.readString();
        option1 = in.readString();
        option2 = in.readString();
        answerNr = in.readInt();
        difficulty = in.readString();
        categoryID = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(question);
        dest.writeString(option1);
        dest.writeString(option2);
        dest.writeInt(answerNr);
        dest.writeString(difficulty);
        dest.writeInt(categoryID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public int getAnswerNr() {
        return answerNr;
    }

    public void setAnswerNr(int answerNr) {
        this.answerNr = answerNr;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public static String[] getAllDifficultyLevels() {
        return new String[]{
                LANGUAGE_ENG,
                LANGUAGE_ID
        };
    }
}
