package com.mapd.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddAssignmentActivity extends AppCompatActivity {
    EditText subjectEditText, titleEditText;
    TextView dueDateEditText;
    String subject;
    String title;
    long dueDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        //Initiating the EditTexts
        subjectEditText = findViewById(R.id.subjectEditText);
        titleEditText = findViewById(R.id.titleEditText);
        dueDateEditText = findViewById(R.id.dueDateEditText);


        final Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dueDateEditText.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        dueDateEditText.setOnClickListener(v -> StartTime.show());

    }

    public void addAssignment(View view){
        //Get the input values
        subject= "";
        title="";
        dueDate = 0;

        subject = subjectEditText.getText().toString().trim();
        title = titleEditText.getText().toString().trim();
        dueDate = getFormattedDate(dueDateEditText.getText().toString());
        //Check for consistency
        Log.e("Subject",subject);
        Log.e("Title",title);
        Log.e("dateFormatted",dueDate+"");



        //Create a JSON String for Assignment Item
        if(checkInput()){

            Long key = System.currentTimeMillis();
            Long currentDate = getFormattedDate(DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
            Log.e("key",key+"");
            Log.e("currentDate",currentDate+"");

            String jsonAssignment = "{ \"datePosted\" : "+currentDate+", " +
                                        "\"dueDate\" : "+dueDate+"," +
                                        "\"key\" : " + key +", " +
                                        "\"subject\" : \"" + subject +"\", " +
                                        "\"title\" : \"" + title +"\"}";
            Log.e("jsons",jsonAssignment);
//            {
//                "datePosted" : 20200710,
//                    "dueDate" : 20200725,
//                    "key" : 12309712,
//                    "subject" : "Android",
//                    "title" : "Assignment 5"
//            }


        }
    }
    public boolean checkInput(){
        return !subject.isEmpty() && !title.isEmpty() && dueDate != 0;
    }
    public long getFormattedDate(String date){
        //mm/dd/yy => yyyymmdd
        if(!date.equals("")) {
        String[] split = date.split("/");

            String day = split[1];
            String month = split[0];
            String year = "20" + split[2];

            if (day.length() == 1){
                day = "0"+ day;
            }
            if (month.length() == 1){
                month = "0"+ month;
            }
            return Long.parseLong(year+month+day);
        }
        return 0;
    }

}