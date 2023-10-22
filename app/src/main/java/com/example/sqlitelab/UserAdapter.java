package com.example.sqlitelab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{

    private Context context;
    private List<User> users;
    private AdapterListener listener;

    public UserAdapter(Context context, AdapterListener listener) {
        this.context = context;
        this.listener=listener;
        users=new ArrayList<>();
    }

    public void addUser(User user){
        users.add(user);
    }

    public void removeUser(int pos){
        users.remove(pos);
    }

    public void cleanData(){
        users.clear();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User userList=users.get(position);
        holder.fName.setText(userList.getFirstName());
        holder.lName.setText(userList.getLastName());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnUpdate(userList);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnDelete(userList.getUid(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView fName,lName;
        private ImageView edit,delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fName=itemView.findViewById(R.id.fName);
            lName=itemView.findViewById(R.id.lName);
            edit=itemView.findViewById(R.id.edit);
            delete=itemView.findViewById(R.id.delete);
        }
    }

}
