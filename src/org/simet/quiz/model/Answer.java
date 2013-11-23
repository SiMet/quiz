package org.simet.quiz.model;

public class Answer {


    public Answer(String content, Boolean proper) {
        this.content = content;
        this.proper = proper;
    }
    public Answer(String content, boolean proper) {
        this(content,Boolean.valueOf(proper));
    }
    
    private long id;
    private String content;
    private Boolean proper;

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

    public boolean isProper() {
        return proper;
    }

    public void setProper(boolean proper) {
        this.proper = proper;
    }

}
