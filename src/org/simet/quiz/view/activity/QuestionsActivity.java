package org.simet.quiz.view.activity;

import java.util.HashMap;
import java.util.List;

import org.simet.quiz.R;
import org.simet.quiz.dao.QuestionDataSource;
import org.simet.quiz.model.Answer;
import org.simet.quiz.model.Question;
import org.simet.quiz.view.fragment.QuestionSectionFragment;
import org.simet.quiz.view.fragment.QuestionSectionFragment.QuestionSectionDelegate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class QuestionsActivity extends FragmentActivity implements QuestionSectionDelegate  {

    private FragmentPagerAdapter questionsPagerAdaper;
    private ViewPager mViewPager;
    private List<Question> questions;
    private QuestionDataSource questionDataSource;
    private HashMap<Question,List<Answer>> answers;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(this.getClass().getName(),"onCreate");
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_questions_list);
        
        questionDataSource = new QuestionDataSource(this);
        questionDataSource.open();
        questions = questionDataSource.findAll();
        questionDataSource.close();
        
        answers = new HashMap<Question, List<Answer>>();
        questionsPagerAdaper = new QuestionsPagerAdapter(getSupportFragmentManager());
        
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(questionsPagerAdaper);
    }

    class QuestionsPagerAdapter extends FragmentPagerAdapter {

        public QuestionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return questions.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            StringBuilder title = new StringBuilder(getString(R.string.question));
            title.append(" ").append(position+1).append("/").append(getCount());
            return title.toString();
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new QuestionSectionFragment(QuestionsActivity.this,QuestionsActivity.this);
            Bundle args = new Bundle();
            args.putSerializable(QuestionSectionFragment.QUESTION, questions.get(position));
            fragment.setArguments(args);
            return fragment;
        }
    }

    private boolean checkQuestion(Question question){
        boolean isCorrect = true;
        
        List<Answer> answers = this.answers.get(question);
        for(Answer answer : question.getAnswers()){
            //We can do this with answer.isProper() ^ answers.containst(answer) but it's not readable ;)
            if(answer.isProper() && !answers.contains(answer) ){
                isCorrect = false;
            }else if(!answer.isProper() && answers.contains(answer)){
                isCorrect = false;
            }
        }
        return isCorrect;
    }
    
    @Override
    public void QuestionConfirmed(Question question, List<Answer> selectedAnswers) {
        answers.put(question, selectedAnswers);
        if(answers.size()==questions.size()){
            StringBuilder msg = new StringBuilder();
            int correct = 0;
            for(int i=0;i<questions.size();++i){
                boolean isCorrect = checkQuestion(questions.get(i));
                if(isCorrect) ++correct;
                
                msg.append(getString(R.string.question)).append(" ").append(i+1).append(" - ")
                .append(isCorrect?getString(R.string.correctly):getString(R.string.incorrectly))
                .append("\n");
            }
            StringBuilder result = new StringBuilder(getString(R.string.your_result))
            .append(" ").append(correct).append("/").append(questions.size()).append("\n\n").append(msg.toString());
            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(R.string.alert_finish_quiz_title)
            .setMessage(result)
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent myIntent = new Intent(QuestionsActivity.this, StartActivity.class);
                    startActivityForResult(myIntent, 0);
                }

            })
            .show();
        }
    }

    @Override
    public void QuestionCorrected(Question question) {
        answers.remove(question);
    }
}
