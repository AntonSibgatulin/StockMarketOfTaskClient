package ru.antonsibgatulin.stockmarketoftaskclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.util.ClassUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.antonsibgatulin.stockmarketoftaskclient.CreateRespondActivity;
import ru.antonsibgatulin.stockmarketoftaskclient.CreateTaskActivity;
import ru.antonsibgatulin.stockmarketoftaskclient.R;
import ru.antonsibgatulin.stockmarketoftaskclient.TaskActivity;
import ru.antonsibgatulin.stockmarketoftaskclient.include.constant.Constant;
import ru.antonsibgatulin.stockmarketoftaskclient.include.tasks.Task;

public class TaskAdapter extends ArrayAdapter<Task> implements View.OnClickListener {

    private Map<View ,Task> map = new HashMap<>();
    private ArrayList<Task> dataSet;
    private Context context;

    public TaskAdapter(ArrayList<Task> data, Context context) {
        super(context, R.layout.fragment_task, data);
        this.dataSet = data;
        this.context = context;
    }


    @Override
    public void onClick(View view) {

        Task task = map.get(view);

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
        TextView viewCount = convertView.findViewById(R.id.countView);
        TextView replyCount = convertView.findViewById(R.id.countReply);
        Button respond = convertView.findViewById(R.id.respone_button);
        respond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreateRespondActivity.class);
                try {
                    intent.putExtra("taskData", Constant.fromObjectToString(task));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                getContext().startActivity(intent);
            }
        });

        String descr = task.getDescription();
        if (descr.length() > 200)
            descr = descr.substring(0, 200) + "...";


        name.setText(task.getName());
        description.setText(descr);
        price.setText(task.getBetterPrice() + "$");
        price_all.setText(task.getPrice() + "$");
        viewCount.setText(String.valueOf(task.getCountView()));
        replyCount.setText(String.valueOf(task.getCountRespond()));

        description.setOnClickListener(this);
        name.setOnClickListener(this);
        price.setOnClickListener(this);
        price_all.setOnClickListener(this);

        map.put(description,task);
        map.put(name,task);
        map.put(price,task);
        map.put(price_all,task);


        return convertView;
    }



}