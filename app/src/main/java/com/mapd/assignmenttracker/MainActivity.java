package com.mapd.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mapd.assignmenttracker.models.Assignment;
import com.mapd.assignmenttracker.models.AssignmentWrapper;
import com.mapd.assignmenttracker.network.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ServiceGenerator.getService().getAssignments().enqueue(new Callback<AssignmentWrapper>() {
            @Override
            public void onResponse(Call<AssignmentWrapper> call, Response<AssignmentWrapper> response) {
                String result;

                if(response.body() != null && response.isSuccessful()){
                     result = response.body().getResults().toString();
                     Log.e("Response",response.body().getResults().get(0).getSubject());
                    Log.e("Code:", result);
                    TextView textView = findViewById(R.id.textView);
                    textView.setText(result);
                }
            }

            @Override
            public void onFailure(Call<AssignmentWrapper> call, Throwable t) {
                Log.e("Errror", t.getMessage()+"");


            }
        });
        //"https://assignment-tracker-d890b.firebaseio.com/assignments.json"



    }

    public void assignmentPage(View view){
        startActivity(new Intent(this, AddAssignmentActivity.class));
    }


}
