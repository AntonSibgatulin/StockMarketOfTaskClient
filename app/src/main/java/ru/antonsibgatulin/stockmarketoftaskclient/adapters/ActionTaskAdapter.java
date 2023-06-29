package ru.antonsibgatulin.stockmarketoftaskclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;

import ru.antonsibgatulin.stockmarketoftaskclient.R;
import ru.antonsibgatulin.stockmarketoftaskclient.TaskActivity;
import ru.antonsibgatulin.stockmarketoftaskclient.include.constant.Constant;
import ru.antonsibgatulin.stockmarketoftaskclient.include.tasks.ActionTask;
import ru.antonsibgatulin.stockmarketoftaskclient.include.tasks.TaskType;

public class ActionTaskAdapter extends ArrayAdapter<ActionTask> implements View.OnClickListener {

    private Context context;


    public ActionTaskAdapter(Context context, ArrayList<ActionTask> actionTasks) {
        super(context, R.layout.activity_task,actionTasks);
        this.context = context;

    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        ActionTask actionTask = getItem(position);
        if(actionTask.isPrivate_task()){
            return;
        }else{
            if(actionTask.getTaskType() == TaskType.CREATE_PURCHASES){
                Intent intent = new Intent(context, TaskActivity.class);
                try {
                    intent.putExtra("taskData", Constant.fromObjectToString(actionTask.getTask()));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                context.startActivity(intent);
            }
        }


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ActionTask actionTask = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_task,parent,false);

        }


        return convertView;

    }
}
