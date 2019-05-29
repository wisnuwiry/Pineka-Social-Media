package com.wisnusaputra.pinekaapp.Quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Pineka.db";
    private static final int DATABASE_VERSION = 1;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;

    private QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                QuizContract.CategoriesTable.TABLE_NAME + "( " +
                QuizContract.CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuizContract.QuestionsTable.TABLE_NAME + " ( " +
                QuizContract.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuizContract.QuestionsTable.COLUMN_LANGUAGE + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuizContract.QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                QuizContract.CategoriesTable.TABLE_NAME + "(" + QuizContract.CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable() {
        Category c1 = new Category("Pkn");
        addCategory(c1);
        Category c2 = new Category("Bhineka");
        addCategory(c2);
        Category c3 = new Category("Sejarah");
        addCategory(c3);
        Category c4 = new Category("Budaya");
        addCategory(c4);
    }

    private void addCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(QuizContract.CategoriesTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Programming, Easy: A is correct",
                "A", "B", 1,
                Question.LANGUAGE_ID, Category.PKN);
        addQuestion(q1);
        Question q2 = new Question("Geography, Medium: B is correct",
                "A", "B", 2,
                Question.LANGUAGE_ENG, Category.PKN);
        addQuestion(q2);
        Question q3 = new Question("Math, Hard: A is correct",
                "A", "B", 1,
                Question.LANGUAGE_ID, Category.SEJARAH);
        addQuestion(q3);
        Question q4 = new Question("Math, Easy: B is correct",
                "A", "B", 2,
                Question.LANGUAGE_ENG, Category.SEJARAH);
        addQuestion(q4);
        Question q5 = new Question("Geography, Medium: A is correct",
                "A", "B", 1,
                Question.LANGUAGE_ID, Category.BHINEKA);
        addQuestion(q5);
        Question q6 = new Question("Math, Hard: B is correct",
                "A", "B", 2,
                Question.LANGUAGE_ENG, Category.BHINEKA);
        addQuestion(q6);
        Question q7 = new Question("Math, Easy: A is correct",
                "A", "B", 1,
                Question.LANGUAGE_ID, Category.BUDAYA);
        addQuestion(q7);
        Question q8 = new Question("Math, Easy: A is correct",
                "A", "B", 1,
                Question.LANGUAGE_ENG, Category.BUDAYA);
        addQuestion(q8);

        Question p1 = new Question("Geography, Medium: B is correct",
                "A", "B", 2,
                Question.LANGUAGE_ENG, Category.PKN);
        addQuestion(p1);

        Question p2 = new Question("Geography, Medium: A is correct",
                "A", "B", 1,
                Question.LANGUAGE_ENG, Category.PKN);
        addQuestion(p2);
        Question p3 = new Question("Geography, Medium: B is correct",
                "A", "B", 2,
                Question.LANGUAGE_ENG, Category.PKN);
        addQuestion(p3);

        Question p4 = new Question("Geography, Medium: A is correct",
                "A", "B", 1,
                Question.LANGUAGE_ENG, Category.PKN);
        addQuestion(p4);
        Question p5 = new Question("Geography, Medium: B is correct",
                "A", "B", 2,
                Question.LANGUAGE_ENG, Category.PKN);
        addQuestion(p5);

        Question p6 = new Question("Geography, Medium: A is correct",
                "A", "B", 1,
                Question.LANGUAGE_ENG, Category.PKN);
        addQuestion(p6);
        Question p7 = new Question("Geography, Medium: B is correct",
                "A", "B", 2,
                Question.LANGUAGE_ENG, Category.PKN);
        addQuestion(p7);

        Question p8 = new Question("Geography, Medium: A is correct",
                "A", "B", 1,
                Question.LANGUAGE_ENG, Category.PKN);
        addQuestion(p8);

        Question p9 = new Question("Geography, Medium: A is correct",
                "A", "B", 1,
                Question.LANGUAGE_ENG, Category.PKN);
        addQuestion(p9);

    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuizContract.QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuizContract.QuestionsTable.COLUMN_LANGUAGE, question.getDifficulty());
        cv.put(QuizContract.QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuizContract.QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.CategoriesTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(QuizContract.CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(QuizContract.CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }

        c.close();
        return categoryList;
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION2)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_LANGUAGE)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(int categoryID, String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuizContract.QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuizContract.QuestionsTable.COLUMN_LANGUAGE + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryID), difficulty};

        Cursor c = db.query(
                QuizContract.QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION2)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_LANGUAGE)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
