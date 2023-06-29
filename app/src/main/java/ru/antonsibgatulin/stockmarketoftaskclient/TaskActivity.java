package ru.antonsibgatulin.stockmarketoftaskclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import ru.antonsibgatulin.stockmarketoftaskclient.include.constant.Constant;
import ru.antonsibgatulin.stockmarketoftaskclient.include.tasks.Task;

public class TaskActivity extends AppCompatActivity {

    private Task task;
    private TextView name,desc,price,price_all;
    private Button respone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String taskData = getIntent().getStringExtra("taskData");
        try {
            task = Constant.fromStringToTask(taskData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setContentView(R.layout.activity_task);

        name = findViewById(R.id.name_task);
        desc = findViewById(R.id.text_task);

        price = findViewById(R.id.price);
        price_all = findViewById(R.id.price_all);

        name.setText(task.getName());
        desc.setText(task.getDescription());
        price.setText(String.valueOf(task.getBetterPrice())+"$");
        price_all.setText(String.valueOf(task.getBetterPrice())+"$");



    }
}