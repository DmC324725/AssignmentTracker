package com.mapd.assignmenttracker.models;

import com.squareup.moshi.Json;

public class Assignment {
    @Json(name="key")
     Integer key;
    @Json(name="datePosted")
     Double datePosted;
    @Json(name="dueDate")
     Double dueDate;
    @Json(name="subject")
     String subject;
    @Json(name="title")
    String title;

    public Assignment(Integer key, Double datePosted, Double dueDate, String subject, String title) {
        this.key = key;
        this.datePosted = datePosted;
        this.dueDate = dueDate;
        this.subject = subject;
        this.title = title;
    }

    public Double getDatePosted() {
        return datePosted;
    }

    public Integer getKey() {
        return key;
    }

    public Double getDueDate() {
        return dueDate;
    }

    public String getSubject() {
        return subject;
    }

    public String getTitle() {
        return title;
    }
}
