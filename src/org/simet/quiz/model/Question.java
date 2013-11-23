package org.simet.quiz.model;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 9021304367068187177L;
    
    
    private long id;
    private String content;
    private List<Answer> answers;

    public Question(String content, List<Answer> answers) {
        this.content = content;
        this.answers = answers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
