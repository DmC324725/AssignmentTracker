package com.mapd.assignmenttracker.models;

import com.squareup.moshi.Json;

import java.util.List;

public class AssignmentWrapper {
    @Json(name="assignmentList")
    List<Assignment> results;
    public List<Assignment> getResults(){
        return results;
    }

}
