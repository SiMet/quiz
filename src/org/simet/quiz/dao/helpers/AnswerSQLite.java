package org.simet.quiz.dao.helpers;

import android.database.sqlite.SQLiteDatabase;

public class AnswerSQLite extends BaseSQLite{

    public static final String COLUMN_PROPER = "proper";
    public static final String COLUMN_QUESTION = "question_id";
    public static final String COLUMN_CONTENT = "content";
    public static final String TABLE_NAME = "answer";
    
    static final String CREATE_TABLE =  "create table "
    + TABLE_NAME + "(" + 
    BaseSQLite.COLUMN_ID + " INTEGER primary key autoincrement, " +
    COLUMN_CONTENT + " text not null, "+
    COLUMN_PROPER + " INTEGER not null, "+
    COLUMN_QUESTION + " long not null," +
    "FOREIGN KEY ("+COLUMN_QUESTION+") REFERENCES "+TABLE_NAME+"("+BaseSQLite.COLUMN_ID+")"+
    ");";

    
    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
    }
    
    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

}
