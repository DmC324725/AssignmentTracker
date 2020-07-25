package com.mapd.assignmenttracker.models;

public class AssignmentItem {
    String subject, title, posted,due;
    long key;

    public AssignmentItem(String subject, String title, String posted, String due, long key) {
        this.subject = subject;
        this.title = title;
        this.posted = posted;
        this.due = due;
        this.key = key;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosted() {
        return posted;
    }

    public void setPosted(String posted) {
        this.posted = posted;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }
}
