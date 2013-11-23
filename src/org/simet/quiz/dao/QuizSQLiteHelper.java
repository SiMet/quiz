package org.simet.quiz.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuizSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_QUESTION = "question";
    public static final String TABLE_ANSWER = "answer";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_QUESTION_CONTENT = "content";
    
    public static final String COLUMN_ANSWER_CONTENT = "content";
    public static final String COLUMN_ANSWER_PROPER = "proper";
    public static final String COLUMN_ANSWER_QUESTION = "question_id";

    private static final String DATABASE_NAME = "simet.quiz.db";
    private static final int DATABASE_VERSION = 1;


    private static final String CREATE_TABLE_QUESTION =  "create table "
        + TABLE_QUESTION + "(" + 
        COLUMN_ID + " INTEGER primary key autoincrement, " +
        COLUMN_QUESTION_CONTENT + " text not null);";
    
    private static final String CREATE_TABLE_ANSWER =  "create table "
            + TABLE_ANSWER + "(" + 
            COLUMN_ID + " INTEGER primary key autoincrement, " +
            COLUMN_ANSWER_CONTENT + " text not null, "+
            COLUMN_ANSWER_PROPER + " INTEGER not null, "+
            COLUMN_ANSWER_QUESTION + " long not null," +
            "FOREIGN KEY ("+COLUMN_ANSWER_QUESTION+") REFERENCES "+TABLE_QUESTION+"("+COLUMN_ID+")"+
            ");";
    
    public QuizSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.w(this.getClass().getName(),"Create new database");
        database.execSQL(CREATE_TABLE_QUESTION);
        database.execSQL(CREATE_TABLE_ANSWER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(),
                "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
            onCreate(db);

    }

}
