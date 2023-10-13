package com.example.sqlitelab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText e1,e2;
    Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1=findViewById(R.id.et1);
        e2=findViewById(R.id.et2);
        b1=findViewById(R.id.btn);
        b2=findViewById(R.id.btnRead);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new bgThread().start();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new bgThread1().start();
            }
        });

    }

    class bgThread extends Thread{
        @Override
        public void run(){
            super.run();

            AppDatabase db= Room.databaseBuilder(getApplicationContext(), AppDatabase.class,"roomDb").build();

            UserDao userDao=db.userDao();
            userDao.insertData(new User(e1.getText().toString(), e2.getText().toString()));
            cleanData();
        }
    }

    class bgThread1 extends Thread{
        @Override
        public void run(){
            super.run();

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "roomDb").build();
            UserDao userDao=db.userDao();
            List<User> users=userDao.getAll();

            Intent intent=new Intent(MainActivity.this,MainActivity1.class);
            intent.putExtra("userList",(Serializable) users);
            startActivity(intent);

        }
    }


    private void cleanData(){
        e1.setText("");
        e2.setText("");
    }
}