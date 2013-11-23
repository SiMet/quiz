package org.simet.quiz.view.activity;

import org.simet.quiz.R;
import org.simet.quiz.utils.DBInitializer;
import org.simet.quiz.view.dialog.QuizProgressDialog;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends Activity {

    private static final String FIRST_RUN_FLAG = "FIRSTRUN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean(FIRST_RUN_FLAG, true);
        if (isFirstRun) {

            //Parse pytania.txt and insert to database
            DBInitializer dbInitializer = new DBInitializer(this);
            QuizProgressDialog pd = new QuizProgressDialog(this);
            pd.setTitle(getString(R.string.processing));
            pd.setMessage(getString(R.string.please_wait));
            dbInitializer.addObserver(pd);
            dbInitializer.run();

            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean(FIRST_RUN_FLAG, false);
            editor.commit();
        }

        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(this.getClass().getName(), "Start clicked");
                Intent myIntent = new Intent(v.getContext(), QuestionsActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }
}
