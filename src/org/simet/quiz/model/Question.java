package org.simet.quiz.model;

import java.util.List;

public class Question {

    private String content;
    private List<Answer> answers;

    public Question(String content, List<Answer> answers) {
        this.content = content;
        this.answers = answers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

}
