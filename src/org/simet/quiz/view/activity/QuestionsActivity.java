package org.simet.quiz.view.activity;

import java.util.List;

import org.simet.quiz.R;
import org.simet.quiz.dao.QuestionDataSource;
import org.simet.quiz.model.Question;
import org.simet.quiz.view.fragment.QuestionSectionFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class QuestionsActivity extends FragmentActivity  {

    private FragmentPagerAdapter questionsPagerAdaper;
    private ViewPager mViewPager;
    private List<Question> questions;
    private QuestionDataSource questionDataSource;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(this.getClass().getName(),"onCreate");
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_questions_list);
        
        questionDataSource = new QuestionDataSource(this);
        questionDataSource.open();
        questions = questionDataSource.findAll();
        questionDataSource.close();
        
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
            Fragment fragment = new QuestionSectionFragment(QuestionsActivity.this);
            Bundle args = new Bundle();
            args.putSerializable(QuestionSectionFragment.QUESTION, questions.get(position));
            fragment.setArguments(args);
            return fragment;
        }
    }
}
