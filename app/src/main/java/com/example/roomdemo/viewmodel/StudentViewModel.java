package com.example.roomdemo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roomdemo.repository.StudentRepository;
import com.example.roomdemo.room.Student;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {
    private StudentRepository repository;
    private LiveData<List<Student>> allStudents;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        repository=new StudentRepository(application);
        allStudents=repository.getAllStudents();
    }

    public void insert(Student student){
        repository.insert(student);
    }

    public void update(Student student){
        repository.update(student);
    }

    public void delete(Student student){
        repository.delete(student);
    }

    public void deleteAllStudents(){
        repository.deleteAllStudents();
    }

    public LiveData<List<Student>> getAllStudents(){
        return allStudents;
    }
}
