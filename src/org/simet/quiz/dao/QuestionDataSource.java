package org.simet.quiz.dao;

import java.util.ArrayList;
import java.util.List;

import org.simet.quiz.dao.helpers.AnswerSQLite;
import org.simet.quiz.dao.helpers.BaseSQLite;
import org.simet.quiz.dao.helpers.QuizSQLiteHelper;
import org.simet.quiz.model.Answer;
import org.simet.quiz.model.Question;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class QuestionDataSource {

    // Database fields
    private SQLiteDatabase database;
    private QuizSQLiteHelper dbHelper;
    private String[] allColumns = { BaseSQLite.COLUMN_ID, AnswerSQLite.COLUMN_CONTENT };
    private AnswerDataSource answerDataSource;

    public QuestionDataSource(Context context) {
        dbHelper = new QuizSQLiteHelper(context);
        answerDataSource = new AnswerDataSource(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        answerDataSource.open();
    }

    public void close() {
        dbHelper.close();
        answerDataSource.close();
    }

    public Question createQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(AnswerSQLite.COLUMN_CONTENT, question.getContent());
        long insertId = database.insert(AnswerSQLite.TABLE_NAME, null, values);
        for(Answer answer : question.getAnswers()){
            answerDataSource.createAnswer(answer, insertId);
        }
        Cursor cursor = database.query(AnswerSQLite.TABLE_NAME, allColumns, BaseSQLite.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Question newQuestion = cursorToQuestion(cursor);
        cursor.close();
        return newQuestion;
    }

    public List<Question> findAll() {
        List<Question> questions = new ArrayList<Question>();

        Cursor cursor = database.query(AnswerSQLite.TABLE_NAME, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question comment = cursorToQuestion(cursor);
            questions.add(comment);
            cursor.moveToNext();
        }
        cursor.close();
        return questions;
    }

    private Question cursorToQuestion(Cursor cursor) {
        Long id = cursor.getLong(0);
        Question question = new Question(cursor.getString(1), answerDataSource.findWithQuestionId(id));
        question.setId(id);
        return question;
    }
}
