package ru.antonsibgatulin.stockmarketoftaskclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ru.antonsibgatulin.stockmarketoftaskclient.include.constant.Constant;
import ru.antonsibgatulin.stockmarketoftaskclient.include.tasks.Task;

public class TaskActivity extends AppCompatActivity {

    private Task task;
    private TextView name,desc,price,price_all,view,reply;
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
        view = findViewById(R.id.countView);
        reply = findViewById(R.id.countReply);


        name.setText(task.getName());
        desc.setText(task.getDescription());
        price.setText(String.valueOf(task.getBetterPrice())+"$");
        price_all.setText(String.valueOf(task.getBetterPrice())+"$");

        view.setText(String.valueOf(task.getCountView()));
        reply.setText(String.valueOf(task.getCountRespond()));

    }



    public void respone(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Constant.RESPONE,listener ->{
            try {
                JSONObject jsonObject = new JSONObject(listener);



            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        },error -> {
            error.printStackTrace();
        }){

        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}