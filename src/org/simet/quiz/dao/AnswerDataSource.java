package org.simet.quiz.dao;

import java.util.ArrayList;
import java.util.List;

import org.simet.quiz.dao.helpers.BaseSQLite;
import org.simet.quiz.dao.helpers.QuestionSQLite;
import org.simet.quiz.dao.helpers.QuizSQLiteHelper;
import org.simet.quiz.model.Answer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AnswerDataSource {
    // Database fields
    private SQLiteDatabase database;
    private QuizSQLiteHelper dbHelper;
    private String[] allColumns = { BaseSQLite.COLUMN_ID, QuestionSQLite.COLUMN_CONTENT,QuestionSQLite.COLUMN_PROPER };

    public AnswerDataSource(Context context) {
        dbHelper = new QuizSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Answer createAnswer(Answer answer, Long questionId) {
        ContentValues values = new ContentValues();
        values.put(QuestionSQLite.COLUMN_CONTENT, answer.getContent());
        values.put(QuestionSQLite.COLUMN_PROPER, answer.isProper());
        values.put(QuestionSQLite.COLUMN_QUESTION, questionId);
        long insertId = database.insert(QuestionSQLite.TABLE_NAME, null, values);
        Cursor cursor = database.query(QuestionSQLite.TABLE_NAME, allColumns, BaseSQLite.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Answer newAnswer = cursorToAnswer(cursor);
        cursor.close();
        return newAnswer;
    }

    public List<Answer> findWithQuestionId(Long questionId) {
        if (questionId == null) {
            throw new IllegalArgumentException();
        }
        List<Answer> answers = new ArrayList<Answer>();
        Cursor cursor = database.rawQuery("select * from " + QuestionSQLite.TABLE_NAME + " where " + QuestionSQLite.COLUMN_QUESTION + " = ?",
                new String[] { questionId.toString() });
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            answers.add(cursorToAnswer(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return answers;
    }

    private Answer cursorToAnswer(Cursor cursor) {
        Answer answer = new Answer(cursor.getString(1), (cursor.getInt(2) == 1));
        answer.setId(cursor.getLong(0));
        return answer;
    }
}
