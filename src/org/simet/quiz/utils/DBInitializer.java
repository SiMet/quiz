package org.simet.quiz.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.simet.quiz.dao.QuestionDataSource;
import org.simet.quiz.model.Question;
import org.simet.quiz.model.factory.QuestionFactory;
import org.simet.quiz.view.dialog.ProgressObserver;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

public class DBInitializer {

    private List<ProgressObserver> observers = new ArrayList<ProgressObserver>();
    private Activity parent;

    public DBInitializer(Activity parent) {
        this.parent = parent;
    }

    private InputStream openFile() throws IOException {
        AssetManager am = parent.getAssets();
        return am.open("pytania.txt");
    }

    private List<String> splitQuestions(InputStream is) {
        List<String> questions = new ArrayList<String>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(is, "UTF-8").useDelimiter("\\$");
            while (scanner.hasNext()) {
                questions.add(scanner.next());
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return questions;
    }

    public void addObserver(ProgressObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ProgressObserver observer) {
        observers.remove(observer);
    }

    // == Notifiers method
    private void processedFinished() {
        for (ProgressObserver o : observers) {
            o.processedFinished();
        }
    }

    private void processingStarted() {
        for (ProgressObserver o : observers) {
            o.processingStarted();
        }
    }

    private void setMaxOperations(int amount) {
        for (ProgressObserver o : observers) {
            o.setMaxOperations(amount);
        }
    }

    private void processedOperationChanged(int value) {
        for (ProgressObserver o : observers) {
            o.processedOperationChanged(value);
        }
    }

    // ===
    public void run() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is;
                try {
                    is = openFile();
                } catch (IOException e1) {
                    Log.wtf(this.getClass().getName(), "Problem with open file with questions");
                    return;
                }

                processingStarted();
                List<String> questions = splitQuestions(is);
                setMaxOperations(questions.size());

                QuestionFactory qf = new QuestionFactory();
                QuestionDataSource questionDataSource = new QuestionDataSource(DBInitializer.this.parent);
                try{
                    questionDataSource.open();
                    for (int i = 0; i < questions.size(); ++i) {
                        Question question = qf.createQuestion(questions.get(i));
                        questionDataSource.createQuestion(question);
                        processedOperationChanged(i);
                    }
                }finally{
                    questionDataSource.close();
                    processedFinished();
                }
                Log.i(this.getClass().getName(), "Parsing finished");
            }

        }).start();
    }
}
