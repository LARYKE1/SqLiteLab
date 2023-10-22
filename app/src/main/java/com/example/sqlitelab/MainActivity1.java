package com.example.sqlitelab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity1 extends AppCompatActivity {

    EditText e1,e2;
    Button b1;
    User users;
    AppDatabase database;
    UserDao userDao;
    private Executor executor= Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        database=AppDatabase.getInstance(this);
        userDao=database.userDao();

        e1=findViewById(R.id.txtModify1);
        e2=findViewById(R.id.txtModifyUser);
        b1=findViewById(R.id.btnUpdate);



        Intent intent = getIntent();
        users = (User) getIntent().getSerializableExtra("user");

        e1.setText(users.getFirstName());
        e2.setText(users.getLastName());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executor.execute(()->{
                    User userUpdated=userDao.getById(users.getUid());
                    userUpdated.setFirstName(e1.getText().toString());
                    userUpdated.setLastName(e2.getText().toString());
                    userDao.update(userUpdated);
                    finish();
                });

            }
        });

    }




}