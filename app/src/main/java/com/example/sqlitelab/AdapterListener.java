package com.example.sqlitelab;

public interface AdapterListener {
    //Communication between adapter and the mainactivity

    void OnUpdate(User user);
    void OnDelete(int id,int pos);
}
