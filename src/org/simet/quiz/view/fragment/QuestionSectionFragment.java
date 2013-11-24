package org.simet.quiz.view.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.simet.quiz.R;
import org.simet.quiz.model.Answer;
import org.simet.quiz.model.Question;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuestionSectionFragment extends Fragment {

    public interface QuestionSectionDelegate{
        public void QuestionConfirmed(Question question, List<Answer> selectedAnswers);
        public void QuestionCorrected(Question question);
    }
    public static final String QUESTION = "question_object";

    private Context context;
    private Question question;
    private HashMap<CheckBox,Answer> checkboxes;
    private Button confirmButton;
    private QuestionSectionDelegate delegate;
    
    public QuestionSectionFragment(Context context,QuestionSectionDelegate delegate) {
        this.context = context;
        this.delegate = delegate;
        this.checkboxes = new HashMap<CheckBox,Answer>();
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.question, container, false);
        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.question_linear_layout);
        TextView questionTextView = (TextView) layout.findViewById(R.id.question_label);
        this.question = (Question) getArguments().getSerializable(QUESTION);
        questionTextView.setText(question.getContent());
        
        this.confirmButton = (Button) rootView.findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new ConfirmOnClickListener());
        
        for(int i=0;i<question.getAnswers().size();++i){
            Answer answer = question.getAnswers().get(i);
            CheckBox checkbox = new CheckBox(context);
            checkbox.setText(answer.getContent());
            checkbox.setId(i);
            checkbox.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
            layout.addView(checkbox);
            checkboxes.put(checkbox,answer);
        }
        return rootView;
    }
    private List<Answer> getSelectedAnswers(){
        List<Answer> answers = new ArrayList<Answer>();
        for(CheckBox checkbox : this.checkboxes.keySet()){
            if(checkbox.isChecked()){
                answers.add(this.checkboxes.get(checkbox));
            }
        }
        return answers;
    }
    
    class ConfirmOnClickListener implements OnClickListener{
        private QuestionSectionFragment self = QuestionSectionFragment.this;
        
        @Override
        public void onClick(View v) {
            self.delegate.QuestionConfirmed(self.question, self.getSelectedAnswers());
            for(CheckBox checkbox : self.checkboxes.keySet()){
                checkbox.setEnabled(false);
            }
            self.confirmButton.setText(getString(R.string.correct));
            self.confirmButton.setOnClickListener(new CorrectOnClickListener());
        }
    }
    
    class CorrectOnClickListener implements OnClickListener{
        private QuestionSectionFragment self = QuestionSectionFragment.this;
        
        @Override
        public void onClick(View v) {
            self.delegate.QuestionCorrected(self.question);
            for(CheckBox checkbox : self.checkboxes.keySet()){
                checkbox.setEnabled(true);
            }
            self.confirmButton.setText(getString(R.string.confirm_button));
            self.confirmButton.setOnClickListener(new ConfirmOnClickListener());
        }
    }
}
