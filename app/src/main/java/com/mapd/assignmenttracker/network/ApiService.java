package com.mapd.assignmenttracker.network;

import com.mapd.assignmenttracker.models.AssignmentWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("assignments.json")
    Call<AssignmentWrapper> getAssignments();
}
