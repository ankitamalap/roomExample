package com.example.roomdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.roomdemo.room.Student;
import com.example.roomdemo.viewmodel.StudentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_STUDENT_REQUEST = 1;
    public static final int EDIT_STUDENT_REQUEST = 2;

    private StudentViewModel studentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btnAddStudent = findViewById(R.id.btnAddStudent);
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditStudentActivity.class);
                startActivityForResult(intent, ADD_STUDENT_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final StudentAdapter adapter = new StudentAdapter();
        recyclerView.setAdapter(adapter);

        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        studentViewModel.getAllStudents().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                adapter.submitList(students);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                studentViewModel.delete(adapter.getStudentAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Student data deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Student student) {
                Intent intent = new Intent(MainActivity.this, AddEditStudentActivity.class);
                intent.putExtra(AddEditStudentActivity.EXTRA_ID, student.getId());
                intent.putExtra(AddEditStudentActivity.EXTRA_NAME, student.getName());
                intent.putExtra(AddEditStudentActivity.EXTRA_EMAIL, student.getEmail());
                intent.putExtra(AddEditStudentActivity.EXTRA_PASSWORD, student.getPassword());
                intent.putExtra(AddEditStudentActivity.EXTRA_BIRTHDATE, student.getBirthday());
                intent.putExtra(AddEditStudentActivity.EXTRA_GENDER, student.getGender());
                startActivityForResult(intent, EDIT_STUDENT_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_STUDENT_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddEditStudentActivity.EXTRA_NAME);
            String email = data.getStringExtra(AddEditStudentActivity.EXTRA_EMAIL);
            String password = data.getStringExtra(AddEditStudentActivity.EXTRA_PASSWORD);
            String birthdate = data.getStringExtra(AddEditStudentActivity.EXTRA_BIRTHDATE);
            String gender = data.getStringExtra(AddEditStudentActivity.EXTRA_GENDER);

            Student student = new Student(name, email, password, birthdate, gender);
            studentViewModel.insert(student);
            Toast.makeText(this, "Student Data Saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_STUDENT_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditStudentActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Data can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = data.getStringExtra(AddEditStudentActivity.EXTRA_NAME);
            String email = data.getStringExtra(AddEditStudentActivity.EXTRA_EMAIL);
            String password = data.getStringExtra(AddEditStudentActivity.EXTRA_PASSWORD);
            String birthdate = data.getStringExtra(AddEditStudentActivity.EXTRA_BIRTHDATE);
            String gender = data.getStringExtra(AddEditStudentActivity.EXTRA_GENDER);

            Student student = new Student(name, email, password, birthdate, gender);
            student.setId(id);
            studentViewModel.update(student);
            Toast.makeText(this, "Data updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                studentViewModel.deleteAllStudents();
                Toast.makeText(this, "All data deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
