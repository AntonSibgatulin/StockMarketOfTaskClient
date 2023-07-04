package ru.antonsibgatulin.stockmarketoftaskclient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import ru.antonsibgatulin.stockmarketoftaskclient.include.constant.Constant;
import ru.antonsibgatulin.stockmarketoftaskclient.include.tasks.Task;

public class CreateRespondActivity extends AppCompatActivity {

    private Task task = null;
    private EditText message_text_respond,price,count_of_day;
    private Button button;

    private RequestQueue requestQueue = null;

    private ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_respond);
        requestQueue = Volley.newRequestQueue(this);
        init();
    }


    public void init(){
        try {
            task = Constant.fromStringToTask(getIntent().getStringExtra("taskData"));
        } catch (IOException e) {
            e.printStackTrace();
            //  throw new RuntimeException(e);
        }
        progressDialog = new ProgressDialog(this);

        checkRespond();

        button = findViewById(R.id.send);
        message_text_respond = findViewById(R.id.message_text_respond);
        price = findViewById(R.id.price);
        count_of_day = findViewById(R.id.count_of_days);
        price.setText(String.valueOf(task.getBetterPrice()));
        count_of_day.setText(String.valueOf(3));



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (message_text_respond.getText().toString().isEmpty())return;
                int pr = Integer.valueOf(price.getText().toString());
                if(pr<=0 || pr >task.getPrice())pr= task.getBetterPrice();

                int countDay =  Integer.valueOf(count_of_day.getText().toString());
                if(countDay <=0)countDay =  1;
                if(countDay>=14)countDay = 14;



                send(task.getId(),message_text_respond.getText().toString(),pr,countDay);
            }
        });

    }

    public void checkRespond(){
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,Constant.CHECK_SEND+"?token="+Constant.getToken(this)+"&id="+task.getId(),response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("type")) {
                    String error = jsonObject.getString("type");
                    if (error.equals("ALREADY_EXIST") || error.equals("MY_TASK")) {

                        prDismiss();
                        finish();
                    }
                }else{
                    prDismiss();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                prDismiss();
            }


        },error -> {
            error.printStackTrace();
            prDismiss();
        }){


        };
        requestQueue.add(stringRequest);
    }




 public void send(Long id,String message,Integer price,Integer countDay){
        progressDialog.show();
        JSONObject jsonObject = new JSONObject();
     try {

         jsonObject.put("message",message);
         jsonObject.put("id",id);
         jsonObject.put("price",price);
         jsonObject.put("countDay",countDay);

     } catch (JSONException e) {
         throw new RuntimeException(e);
     }


     StringRequest stringRequest = new StringRequest(Request.Method.POST,Constant.RESPONE+"?token="+Constant.getToken(this),response -> {

         try {
             JSONObject json = new JSONObject(response);

            prDismiss();
            finish();

         } catch (JSONException e) {
             throw new RuntimeException(e);
         }
         prDismiss();

     },error -> {
         error.printStackTrace();
         prDismiss();
     }){

         @Override
         public String getBodyContentType() {
             return "application/json; charset=utf-8;";
         }

         @Override
         public byte[] getBody() throws AuthFailureError {

             return jsonObject.toString().getBytes(StandardCharsets.UTF_8);
         }

         @Nullable
         @Override
         protected Map<String, String> getParams() throws AuthFailureError {
             Map<String,String> map = new HashMap<>();
             map.put("token",Constant.getToken(CreateRespondActivity.this));
             return map;
         }
     };



     requestQueue.add(stringRequest);
 }

 public void prDismiss(){
        progressDialog.dismiss();
 }
}