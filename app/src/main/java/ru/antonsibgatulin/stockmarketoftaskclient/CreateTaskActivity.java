package ru.antonsibgatulin.stockmarketoftaskclient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import ru.antonsibgatulin.stockmarketoftaskclient.include.constant.Constant;

public class CreateTaskActivity extends AppCompatActivity {

    private EditText name,description,price,priceBetter;
    private Button create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        init();
    }


    private void init(){

        name = findViewById(R.id.name_of_task);
        description = findViewById(R.id.description_of_task);
        price = findViewById(R.id.priceTask);
        priceBetter = findViewById(R.id.priceBetterTask);

        create = findViewById(R.id.create);
        ImageView imageView = findViewById(R.id.cancel);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCreate();
            }
        });




    }

    private void sendCreate() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",name.getText().toString());
            jsonObject.put("description",description.getText().toString());
            jsonObject.put("price", Integer.valueOf(price.getText().toString()));
            jsonObject.put("betterPrice",Integer.valueOf(priceBetter.getText().toString()));


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Constant.TASK_CREATE+"?token="+Constant.getToken(this),response -> {

            finish();

        },error -> {

        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("token",Constant.getToken(CreateTaskActivity.this));
                return map;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8;";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonObject.toString().getBytes(StandardCharsets.UTF_8);
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}