package org.simet.quiz.dao.helpers;

import android.database.sqlite.SQLiteDatabase;

public class QuestionSQLite extends BaseSQLite {
    public static final String TABLE_NAME = "question";
    public static final String COLUMN_CONTENT = "content";
    
    static final String CREATE_TABLE =  "create table "
    + TABLE_NAME + "(" + 
    COLUMN_ID + " INTEGER primary key autoincrement, " +
    COLUMN_CONTENT + " text not null);";


    
    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
    }
    
    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
}
