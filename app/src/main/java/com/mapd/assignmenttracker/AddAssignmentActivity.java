package com.mapd.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class AddAssignmentActivity extends AppCompatActivity {
    EditText subjectEditText, titleEditText;
    TextView dueDateEditText;
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


    public void showDatePicker(View view){
        //Show a date picker and then return the value of the selected date back to the date textview

    }
}