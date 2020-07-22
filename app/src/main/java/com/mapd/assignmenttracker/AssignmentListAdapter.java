package com.mapd.assignmenttracker;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mapd.assignmenttracker.databinding.ViewAssignmentListBinding;
import com.mapd.assignmenttracker.models.Assignment;

import java.util.ArrayList;
import java.util.List;

public class AssignmentListAdapter extends RecyclerView.Adapter<AssignmentListAdapter.AssignmentListViewHolder> {

    private List<Assignment> assignmentList = new ArrayList<>();

    public void setAssignmentList(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }

    @NonNull
    @Override
    public AssignmentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AssignmentListViewHolder.from(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentListViewHolder holder, int position) {
        holder.bind(assignmentList.get(position));
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    public static class AssignmentListViewHolder extends RecyclerView.ViewHolder {
        private ViewAssignmentListBinding binding;

        public AssignmentListViewHolder(@NonNull ViewAssignmentListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Assignment assignment) {
            String title = itemView.getContext().getString(
                    R.string.main_title,
                    assignment.getSubject().toUpperCase(),
                    assignment.getTitle().toUpperCase());

            String dueDate = itemView.getContext().getString(
                    R.string.due_date_assign,
                    assignment.getDueDate());

            binding.itemTitle.setText(title);
            binding.dueDate.setText(dueDate);

        }

        public static AssignmentListViewHolder from(ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ViewAssignmentListBinding binding = ViewAssignmentListBinding.inflate(inflater, parent, false);
            return new AssignmentListViewHolder(binding);
        }
    }

}
