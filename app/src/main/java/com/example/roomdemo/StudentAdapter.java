package com.example.roomdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdemo.room.Student;

public class StudentAdapter extends ListAdapter<Student, StudentAdapter.StudentHolder> {

    private OnItemClickListener listener;

    public StudentAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Student> DIFF_CALLBACK = new DiffUtil.ItemCallback<Student>() {
        @Override
        public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.getName().equals(newItem.getName())
                    && oldItem.getEmail().equals(newItem.getEmail())
                    && oldItem.getPassword().equals(newItem.getPassword())
                    && oldItem.getBirthday().equals(newItem.getBirthday())
                    && oldItem.getGender().equals(newItem.getGender());
        }
    };

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_list, parent, false);
        return new StudentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
        Student student = getItem(position);
        holder.textName.setText(student.getName());
        holder.textEmail.setText(student.getEmail());
        holder.textPassword.setText(student.getPassword());
        holder.textBirthdate.setText(student.getBirthday());
        holder.textGender.setText(student.getGender());
    }


    public Student getStudentAt(int position) {
        return getItem(position);
    }

    class StudentHolder extends RecyclerView.ViewHolder {
        private TextView textName, textEmail, textPassword, textBirthdate, textGender;

        public StudentHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.textName);
            textEmail = itemView.findViewById(R.id.textEmail);
            textPassword = itemView.findViewById(R.id.textPassword);
            textBirthdate = itemView.findViewById(R.id.textBirthdate);
            textGender = itemView.findViewById(R.id.textGender);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Student student);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
