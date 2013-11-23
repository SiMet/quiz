package org.simet.quiz.view.fragment;

import org.simet.quiz.R;
import org.simet.quiz.model.Question;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuestionSectionFragment extends Fragment {

    public static final String QUESTION = "question_object";

    private Context context;
    
    public QuestionSectionFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.question, container, false);
        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.question_linear_layout);
        TextView questionTextView = (TextView) layout.findViewById(R.id.question_label);
        Question question = (Question) getArguments().getSerializable(QUESTION);
        questionTextView.setText(question.getContent());
        for(int i=0;i<question.getAnswers().size();++i){
            CheckBox checkbox = new CheckBox(context);
            checkbox.setText(question.getAnswers().get(i).getContent());
            checkbox.setId(i);
            checkbox.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
            layout.addView(checkbox);
        }
        return rootView;
    }
}
