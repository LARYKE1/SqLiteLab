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

public class MainActivity1 extends AppCompatActivity {

    TextView t1;
    EditText e1;
    Button b1,b2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        t1=findViewById(R.id.txtUser);
        e1=findViewById(R.id.txtModify1);
        b1=findViewById(R.id.btnUpdate);
        b2=findViewById(R.id.btnDelete);


        Intent intent = getIntent();
        List<User> users = (List<User>) intent.getSerializableExtra("userList");


        TextView textView = findViewById(R.id.txtUser);

        StringBuilder userNames = new StringBuilder();
        for (User user : users) {
            userNames.append(user.getUid()+" "+user.getFirstName()+" "+user.getLastName()).append("\n");
        }

        textView.setText(userNames.toString());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new bgThread().start();
            }
        });

    }

    class bgThread extends Thread{
        @Override
        public void run(){
            super.run();


            String userId=e1.getText().toString();
            if(!userId.isEmpty()){
                try{
                    int user=Integer.parseInt(userId);
                    deleteUserById(user);

                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }else{

            }

        }
    }

    private void deleteUserById(int userId){
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "roomDb").build();

        UserDao userDao = db.userDao();

        // Delete the user by ID
        userDao.delete(userId);
        View parentView=findViewById(R.id.activitymain1);
        Snackbar snackbar= Snackbar.make(parentView,"User deleted successfully", Snackbar.LENGTH_SHORT);
        snackbar.show();

    }

    private void updateName(String name){

    }


}