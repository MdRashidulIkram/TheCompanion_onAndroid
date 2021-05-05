package com.example.the_companion;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<Task> {

    private ArrayList<Task> tasks;
    private Context context;

    public CustomArrayAdapter(Context context, ArrayList<Task> tasks){
        super(context,0, tasks);
        this.tasks = tasks;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = convertView;

        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_custom_array_adapter,parent,false);
        }
        Task task = tasks.get(position);

        TextView taskDescription = view.findViewById(R.id.taskdescription);

        taskDescription.setText(task.getTaskDescription());
        return view;
    }

}