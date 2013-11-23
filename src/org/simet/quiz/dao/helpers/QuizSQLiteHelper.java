package org.simet.quiz.dao.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuizSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "simet.quiz.db";
    private static final int DATABASE_VERSION = 1;

    public QuizSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.w(this.getClass().getName(), "Create new database");
        QuestionSQLite.onCreate(database);
        AnswerSQLite.onCreate(database);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        AnswerSQLite.onUpgrade(db, oldVersion, newVersion);
        QuestionSQLite.onUpgrade(db, oldVersion, newVersion);
        onCreate(db);

    }

}
