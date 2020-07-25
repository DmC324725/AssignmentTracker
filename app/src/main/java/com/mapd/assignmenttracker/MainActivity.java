package com.mapd.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mapd.assignmenttracker.models.AssignmentAdapter;
import com.mapd.assignmenttracker.models.AssignmentItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<AssignmentItem> assignments;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    protected void onResume() {
        super.onResume();
        new JsonTask().execute("https://assignment-tracker-d890b.firebaseio.com/assignments/assignmentList.json");

    }

    public void assignmentPage(View view){
        startActivity(new Intent(this, AddAssignmentActivity.class));
    }



    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            
            try {
                JSONObject res = new JSONObject(result);

                //Get the results
                assignments = getAssignments(res);
                mAdapter = new AssignmentAdapter(MainActivity.this, assignments);

                recyclerView.setAdapter(mAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            //txtJson.setText(result);
        }
        
        public List<AssignmentItem> getAssignments(JSONObject jsonObject){
            List<AssignmentItem> assignmentItems = new ArrayList<>();
            try{

                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String subject, title, dueDate, datePosted;
                    long akey;
                    String key = keys.next();
                    if (jsonObject.get(key) instanceof JSONObject) {
                        subject = (String) ((JSONObject) jsonObject.get(key)).get("subject");
                        // do something with jsonObject here

                        title = (String) ((JSONObject) jsonObject.get(key)).get("title");

                        dueDate = formatLongDate((int) ((JSONObject) jsonObject.get(key)).get("dueDate"));


                        datePosted = formatLongDate((int) ((JSONObject) jsonObject.get(key)).get("datePosted"));

                        akey =  Long.parseLong(key);

                        assignmentItems.add(new AssignmentItem(subject, title, datePosted, dueDate, akey));

                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                Log.e("Errror",e.getMessage()+"");
            }
            return assignmentItems;
        }
        public String formatLongDate(long date){
            String newDate = date+"";
            String[] months ={"","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
            String month = months[Integer.parseInt(newDate.substring(4,6))];

            return  newDate.substring(6)+" "+month+", "+newDate.substring(0,4);
        }
    }


}

