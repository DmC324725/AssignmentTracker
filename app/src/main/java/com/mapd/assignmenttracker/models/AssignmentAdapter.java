package com.mapd.assignmenttracker.models;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mapd.assignmenttracker.AddAssignmentActivity;
import com.mapd.assignmenttracker.MainActivity;
import com.mapd.assignmenttracker.R;

import java.io.DataOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {
    private List<AssignmentItem> assignments = new ArrayList<>();
    private Context mContext;

    public AssignmentAdapter(Context context, List<AssignmentItem> assignments) {
        this.mContext = context;
        this.assignments = assignments;
    }

    @NonNull
    @Override
    public AssignmentAdapter.AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_item_view, parent, false);
        AssignmentViewHolder viewHolder = new AssignmentViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentAdapter.AssignmentViewHolder holder, int position) {

        holder.subject.setTag(assignments.get(position).getKey());

        holder.subject.setText(assignments.get(position).getSubject());
        holder.title.setText(assignments.get(position).getTitle());
        holder.datePosted.setText(assignments.get(position).getPosted());
        holder.dueDate.setText(assignments.get(position).getDue());
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder{

        public TextView subject;
        public TextView title;
        public TextView dueDate;
        public TextView datePosted;


        public AssignmentViewHolder(View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.assignment_subject_textView);
            title = itemView.findViewById(R.id.assignment_title_textView);
            dueDate = itemView.findViewById(R.id.assignment_dueDate_TextView);
            datePosted = itemView.findViewById(R.id.assignment_datePosted_TextView);

            itemView.setOnClickListener(view -> {
//                  Implement ONClick

                TextView subjectTextView = view.findViewById(R.id.assignment_subject_textView);
                String subject = subjectTextView.getText().toString();
                String title = ((TextView)view.findViewById(R.id.assignment_title_textView)).getText().toString();
                String duedate = ((TextView)view.findViewById(R.id.assignment_dueDate_TextView)).getText().toString();
                String datePosted = ((TextView)view.findViewById(R.id.assignment_datePosted_TextView)).getText().toString();

                String key =subjectTextView.getTag().toString();
                ArrayList<String> item = new ArrayList<>();
                item.add(subject);
                item.add(title);
                item.add(duedate);
                item.add(datePosted);
                item.add(key);
                Intent intent = new Intent(view.getContext(), AddAssignmentActivity.class);
                intent.putExtra("assignment",item);
                mContext.startActivity(intent);
            });
            itemView.setOnLongClickListener(v -> {
                long key =(Long) v.findViewById(R.id.assignment_subject_textView).getTag();
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                String url = "https://assignment-tracker-d890b.firebaseio.com/assignments/assignmentList/"+key+".json";
                                deletePost(url);
                                Toast.makeText(v.getContext(),"Assignment has been deleted",Toast.LENGTH_LONG).show();

                                assignments.remove(getAdapterPosition());
                                notifyItemRemoved(getAdapterPosition());

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to delete the assignment?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


                return true;
            });

        }
        public void deletePost(String urlAdress) {
            Thread thread = new Thread(() -> {
                try {
                    URL url = new URL(urlAdress);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("X-HTTP-Method-Override", "DELETE");
                    conn.setRequestProperty("Accept","application/json");
                    //conn.setDoOutput(true);
                    conn.setDoInput(true);
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes("");

                    os.flush();
                    os.close();

                    Log.e("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.e("MSG" , conn.getResponseMessage());
                    if(conn.getResponseCode() == 200){

                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("PostFailed",e.getMessage()+"");
                }
            });

            thread.start();
        }
    }

}
