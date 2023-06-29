package ru.antonsibgatulin.stockmarketoftaskclient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ru.antonsibgatulin.stockmarketoftaskclient.include.constant.Constant;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String token = sharedPreferences.getString("token",null);
        boolean firstly = sharedPreferences.getBoolean("firstly", true);

        Intent intent = null;
       /* if(true==true){
            startActivity(new Intent(this,HomeActivity.class));
            finish();
            return;
        }

        */
        if (firstly) {
            Constant.putBoolean(this, "user", "firstly", false);
            intent = new Intent(this, OnBoardActivity.class);
        } else if (token == null) {
            intent = new Intent(MainActivity.this, AuthActivity.class);
        } else {
            checkAuth(token);
        }


        if (intent != null) {
            Handler handler = new Handler();

            final Intent finalIntent = intent;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(finalIntent);
                    finish();
                }
            }, 1500);
        }

    }


    public void checkAuth(String token){
        long timeStart = System.currentTimeMillis();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.CHECK_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("auth").equals("OK")){

                        startActivity(new Intent(MainActivity.this,HomeActivity.class));
                        finish();
                    }else{
                        Constant.clearData(MainActivity.this,"user");
                        long timeEnd = System.currentTimeMillis();

                        long time = 0L;

                        if(timeEnd-timeStart<1500){
                            time = timeEnd-timeStart;
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(MainActivity.this, AuthActivity.class));
                                MainActivity.this.finish();
                            }
                        },time);
                    }
                } catch (JSONException e) {

                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, AuthActivity.class));
                        MainActivity.this.finish();
                    }
                },1500);
                //error.printStackTrace();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();

                map.put("token",token);

                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);





    }
}