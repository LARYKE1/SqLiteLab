package com.example.sqlitelab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements  AdapterListener{

    EditText e1,e2;
    Button b1,b2;
    AppDatabase labDb;
    UserDao userDao;
    private UserAdapter userAdapter;
    //I use executor instead of AsyncTask because AsyncTask is deprecated
    //It executes submitted Runnable tasks
    private Executor executor= Executors.newSingleThreadExecutor();
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        labDb = AppDatabase.getInstance(getApplicationContext());
        userDao= labDb.userDao();

        rv=findViewById(R.id.recyclerView);

        e1=findViewById(R.id.et1);
        e2=findViewById(R.id.et2);
        b1=findViewById(R.id.btn);
        b2=findViewById(R.id.btnRead);

        userAdapter=new UserAdapter(getApplicationContext(),this);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executor.execute(()->{
                    if(e1!=null && e2!=null){
                        addUser();
                    }else{
                        Toast.makeText(getApplicationContext(),"INSERT DATA", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rv.setAdapter(userAdapter);
                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
               executor.execute(()->{

                   getData();
               });

            }
        });

    }

    //get all users
    //use useradapter to display in the view
    private void getData(){
        //if the button is pressed multiple times, this will cause to have duplicate data
        //but cleanData will delete the data from the view and display only the current list
        userAdapter.cleanData();
        List<User>userList=userDao.getAll();
        for(User user : userList){
            userAdapter.addUser(user);
        }
    }

    private void addUser(){
        UserDao userDao=labDb.userDao();
        userDao.insertData(new User(e1.getText().toString(), e2.getText().toString()));
        cleanData();

    }
    private void cleanData(){
        e1.setText("");
        e2.setText("");
    }

    @Override
    public void OnUpdate(User user) {
        Intent intent=new Intent(this,MainActivity1.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    @Override
    public void OnDelete(int id, int pos) {
        executor.execute(()->{
            userDao.delete(id);
            //to use the UI thread because it do not consume that much and it can handle, compared to the line above that require a background thread
            runOnUiThread(()->{
                userAdapter.removeUser(pos);
                Snackbar snackbar= Snackbar.make(rv,"User deleted successfully", Snackbar.LENGTH_SHORT);
                snackbar.show();
            });

        });
    }

}