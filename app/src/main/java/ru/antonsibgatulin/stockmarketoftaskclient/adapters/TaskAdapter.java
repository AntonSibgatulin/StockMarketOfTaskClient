package ru.antonsibgatulin.stockmarketoftaskclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;

import ru.antonsibgatulin.stockmarketoftaskclient.CreateTaskActivity;
import ru.antonsibgatulin.stockmarketoftaskclient.R;
import ru.antonsibgatulin.stockmarketoftaskclient.TaskActivity;
import ru.antonsibgatulin.stockmarketoftaskclient.include.constant.Constant;
import ru.antonsibgatulin.stockmarketoftaskclient.include.tasks.Task;

public class TaskAdapter extends ArrayAdapter<Task> implements View.OnClickListener {

    private ArrayList<Task> dataSet;
    private Context context;

    public TaskAdapter(ArrayList<Task> data, Context context) {
        super(context, R.layout.fragment_task, data);
        this.dataSet = data;
        this.context = context;
    }


    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        Task task = getItem(position);
        Intent intent = new Intent(context, TaskActivity.class);
        try {
            intent.putExtra("taskData", Constant.fromObjectToString(task));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        context.startActivity(intent);


    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_task, parent, false);
        }

        TextView description = convertView.findViewById(R.id.text_task);
        TextView price = convertView.findViewById(R.id.price);
        TextView price_all = convertView.findViewById(R.id.price_all);
        TextView name = convertView.findViewById(R.id.name_task);


        name.setText(task.getName());
        description.setText(task.getDescription());
        price.setText(task.getBetterPrice() + "$");
        price_all.setText(task.getPrice() + "$");


        return convertView;
    }


}