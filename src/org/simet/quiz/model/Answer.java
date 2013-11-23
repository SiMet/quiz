package org.simet.quiz.model;

public class Answer {

    public Answer(String content, Boolean proper) {
        this.content = content;
        this.proper = proper;
    }

    private String content;
    private Boolean proper;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isProper() {
        return proper;
    }

    public void setProper(boolean proper) {
        this.proper = proper;
    }

}
