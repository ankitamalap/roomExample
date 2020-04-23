package com.example.roomdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditStudentActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.example.architecturecomponentsexample.EXTRA_ID";
    public static final String EXTRA_NAME =
            "com.example.architecturecomponentsexample.EXTRA_NAME";
    public static final String EXTRA_EMAIL =
            "com.example.architecturecomponentsexample.EXTRA_EMAIL";
    public static final String EXTRA_PASSWORD =
            "com.example.architecturecomponentsexample.EXTRA_PASSWORD";
    public static final String EXTRA_BIRTHDATE =
            "com.example.architecturecomponentsexample.EXTRA_BIRTHDATE";
    public static final String EXTRA_GENDER =
            "com.example.architecturecomponentsexample.EXTRA_GENDER";

    private EditText textName, textEmail, textPassword, textBirthdate, textGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        textBirthdate = findViewById(R.id.textBirthdate);
        textGender = findViewById(R.id.textGender);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        //for update existing note
        //intent takes all data
        Intent intent = getIntent();

        //check if given id is present then edit note else add new note
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Data");
            textName.setText(intent.getStringExtra(EXTRA_NAME));
            textEmail.setText(intent.getStringExtra(EXTRA_EMAIL));
            textPassword.setText(intent.getStringExtra(EXTRA_PASSWORD));
            textBirthdate.setText(intent.getStringExtra(EXTRA_BIRTHDATE));
            textGender.setText(intent.getStringExtra(EXTRA_GENDER));
        } else {
            setTitle("Add Data");
        }
    }

    private void saveNote() {
        String name = textName.getText().toString();
        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();
        String birthdate = textBirthdate.getText().toString();
        String gender = textGender.getText().toString();

        if (name.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty() || birthdate.trim().isEmpty() || gender.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a data", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_EMAIL, email);
        data.putExtra(EXTRA_PASSWORD, password);
        data.putExtra(EXTRA_BIRTHDATE, birthdate);
        data.putExtra(EXTRA_GENDER, gender);

        //check id for edit note data
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_data:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
