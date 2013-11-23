package org.simet.quiz.activities;

import org.simet.quiz.R;
import org.simet.quiz.utils.DBInitializer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        
        DBInitializer dbInitializer = new DBInitializer(this);
        QuizProgressDialog pd = new QuizProgressDialog(this);
        pd.setTitle("Processing...");
		pd.setMessage("Please wait");
        dbInitializer.addObserver(pd);
        dbInitializer.run();
        
        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(this.getClass().getName(),"Start clicked");
			}
		});
    }    
}
