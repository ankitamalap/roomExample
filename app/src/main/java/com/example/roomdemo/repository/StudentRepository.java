package com.example.roomdemo.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.roomdemo.room.Student;
import com.example.roomdemo.room.StudentDao;
import com.example.roomdemo.room.StudentDatabase;

import java.util.List;

public class StudentRepository {

    private StudentDao studentDao;
    private LiveData<List<Student>> allStudents;

    public StudentRepository(Application application) {
        StudentDatabase database = StudentDatabase.getInstance(application);
        studentDao = database.studentDao();
        allStudents = studentDao.getAllStudents();
    }

    public void insert(Student student) {
        new InsertStudentAsyncTask(studentDao).execute(student);
    }

    private static class InsertStudentAsyncTask extends AsyncTask<Student, Void, Void> {

        private StudentDao studentDao;

        private InsertStudentAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.insert(students[0]);
            return null;
        }
    }

    public void update(Student student) {
        new UpdateStudentAsyncTask(studentDao).execute(student);
    }

    private static class UpdateStudentAsyncTask extends AsyncTask<Student, Void, Void> {

        private StudentDao studentDao;

        private UpdateStudentAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.update(students[0]);
            return null;
        }
    }

    public void delete(Student student) {
        new DeleteStudentAsyncTask(studentDao).execute(student);
    }

    private static class DeleteStudentAsyncTask extends AsyncTask<Student, Void, Void> {

        private StudentDao studentDao;

        private DeleteStudentAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.delete(students[0]);
            return null;
        }
    }

    public void deleteAllStudents() {
        new DeleteAllStudentsAsyncTask(studentDao).execute();
    }

    private static class DeleteAllStudentsAsyncTask extends AsyncTask<Void, Void, Void> {

        private StudentDao studentDao;

        private DeleteAllStudentsAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            studentDao.deleteAllStudents();
            return null;
        }
    }

    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }
}
