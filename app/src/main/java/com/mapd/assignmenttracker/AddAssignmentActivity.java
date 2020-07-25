package com.mapd.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddAssignmentActivity extends AppCompatActivity {
    EditText subjectEditText, titleEditText;
    TextView dueDateEditText;
    Boolean isEditing = false;
    String subject;
    String url = "https://assignment-tracker-d890b.firebaseio.com/assignments/assignmentList.json";
    String title;

    String initSubject, initTitle, initDueDate;
    long dueDate, currentDate, key;
    SimpleDateFormat formatter;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        initSubject = initTitle = initDueDate = "";
        currentDate = key = 0;
        mContext = AddAssignmentActivity.this;
        formatter = new SimpleDateFormat("dd MMM, yyyy");
        //Initiating the EditTexts
        subjectEditText = findViewById(R.id.subjectEditText);
        titleEditText = findViewById(R.id.titleEditText);
        dueDateEditText = findViewById(R.id.dueDateEditText);

        ArrayList<String> assignmentPassed = getIntent().getStringArrayListExtra("assignment");
        if(assignmentPassed != null){
            isEditing = true;
            subjectEditText.setText(assignmentPassed.get(0));
            titleEditText.setText(assignmentPassed.get(1));
            dueDateEditText.setText(assignmentPassed.get(2));
            key = Long.parseLong(assignmentPassed.get(4));

            initSubject = assignmentPassed.get(0);
            initTitle = assignmentPassed.get(1);
            initDueDate = assignmentPassed.get(2);

            //call a function to fill the fields with previous data
        }

        final Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                String strDate = formatter.format(newDate.getTime());
                dueDateEditText.setText(strDate);
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
        currentDate = getFormattedDate(formatter.format(new Date()));

        if(isEditing && initSubject.equals(subject) && initTitle.equals(title) && initDueDate.equals(dueDateEditText.getText().toString())){
            Log.e("noChange","No Change Made");
            finish();
            return;
        }
        if(checkInput()){
            if(key == 0)
                key = System.currentTimeMillis();
            sendPost((url),jsonString());
            Toast.makeText(AddAssignmentActivity.this,"Assignment has been added!",Toast.LENGTH_LONG).show();

            clearInputs();
        }else{
            Toast.makeText(AddAssignmentActivity.this,"Please enter valid inputs",Toast.LENGTH_LONG).show();
        }
    }

    public String jsonString(){

        return "{ \""+key+"\":{ \"datePosted\" : "+currentDate+", " +
                "\"dueDate\" : "+dueDate+"," +
                "\"key\" : " + key +", " +
                "\"subject\" : \"" + subject +"\", " +
                "\"title\" : \"" + title +"\"}}";
    }

    public void sendPost(String urlAdress, String jsonString) {
        Thread thread = new Thread(() -> {
            try {
                URL url = new URL(urlAdress);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PATCH");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(jsonString);

                os.flush();
                os.close();

                Log.e("STATUS", String.valueOf(conn.getResponseCode()));
                Log.e("MSG" , conn.getResponseMessage());
                if(conn.getResponseCode() == 200){
                    finish();
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("PostFailed",e.getMessage()+"");
            }
        });

        thread.start();
    }


    public void exitAssignment(View view){
        finish();
    }
    public void clearInputs(){
        subjectEditText.setText("");
        titleEditText.setText("");
        dueDateEditText.setText("");
    }
    public boolean checkInput(){
        return !subject.isEmpty() && !title.isEmpty() && dueDate != 0 && dueDate>currentDate;
    }
    public long getFormattedDate(String date){
        if(!date.equals("")) {
            Log.e("initDate",date);
            String[] months ={"","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
            String month=date.substring(3,6);
            for(int i=0;i<months.length;i++) {
                if(months[i].equals(month)){
                    month =i+"";
                }
            }
            if(month.length()==1)
                month = "0"+month;
            //
            String day = date.substring(0,2);
            String year = date.substring(8);
            Log.e("day",day+",");
            Log.e("month",month+",");
            Log.e("year",year+",");

            return Long.parseLong(year+month+day);
        }
        return 0;
    }

}