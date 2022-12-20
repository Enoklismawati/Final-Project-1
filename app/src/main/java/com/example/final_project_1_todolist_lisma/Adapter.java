package com.example.final_project_1_todolist_lisma;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    ArrayList task_id, task_title;

    Adapter(Context context, ArrayList task_id, ArrayList task_title) {
        this.context = context;
        this.task_id = task_id;
        this.task_title = task_title;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        holder.idTextView.setText(String.valueOf(task_id.get(position)));
        holder.titleTextView.setText(String.valueOf(task_title.get(position)));

//        holder.task_complete.setOnClickListener(new View.OnClickListener() {
    }

    @Override
    public int getItemCount() {
        Log.d("size2", String.valueOf(task_title.size()));
        return task_title.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView idTextView, titleTextView;
        Button completeBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.idTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            completeBtn = itemView.findViewById(R.id.completeBtn);
            completeBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Database myDB = new Database(context);
            myDB.deleteOneTask(idTextView.getText().toString());
            task_title.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), task_title.size());
        }
    }
}
