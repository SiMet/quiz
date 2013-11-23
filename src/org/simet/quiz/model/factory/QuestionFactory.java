package org.simet.quiz.model.factory;

import java.util.ArrayList;
import java.util.List;

import org.simet.quiz.model.Answer;
import org.simet.quiz.model.Question;

import android.util.Log;

public class QuestionFactory {

    public Question createQuestion(String string) {
        String[] splits = string.split(":a:");
        if (splits.length != 2) {
            Log.wtf(this.getClass().getName(), "Error during parse question: " + string);
            throw new IllegalArgumentException("Error during parse question");
        }
        String content = splits[0];
        String answersString = splits[1];
        List<Answer> answers = createAnswers(answersString);
        return new Question(content, answers);
    }

    private int getNextPos(int truePos, int falsePos) {
        if (truePos < 0)
            return falsePos;
        if (falsePos < 0)
            return truePos;
        return truePos < falsePos ? truePos : falsePos;
    }

    public List<Answer> createAnswers(String string) {
        List<Answer> answers = new ArrayList<Answer>();
        while (string.length() > 0) {
            int trueQuestionPos = string.indexOf(":t:");
            int falseQuestionPos = string.indexOf(":f:");

            int end = getNextPos(trueQuestionPos, falseQuestionPos);
            if (end < 0)
                break;
            boolean isProper = (end == trueQuestionPos);

            answers.add(new Answer(string.substring(0, end), isProper));
            string = string.substring(end + 3);

        }
        return answers;
    }
}
