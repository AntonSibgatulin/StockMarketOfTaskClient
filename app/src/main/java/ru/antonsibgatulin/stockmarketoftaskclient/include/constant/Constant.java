package ru.antonsibgatulin.stockmarketoftaskclient.include.constant;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Patterns;

import com.android.volley.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.IOException;
import java.util.Map;

import ru.antonsibgatulin.stockmarketoftaskclient.include.tasks.Task;
import ru.antonsibgatulin.stockmarketoftaskclient.include.user.User;

public class Constant {
    public final static String HOST = "http://192.168.0.106:8080/";
    public final static String HOME = HOST + "api/v1/";
    public final static String LOGIN = HOME + "auth/";
    public final static String REG = HOME + "reg/";

    public final static String PROFILE = HOME+"profile/";
    public static final String CHECK_LOGIN =LOGIN+"check" ;
    public static final String GET_ME = PROFILE+"getMe/";

    public static final String TASK = HOME+"task/";
    public static final String TASK_CREATE = TASK+"create";
    public static  final String STOCK_MARKET = HOME+"stockmarket/";

    public static final String STOCK_MARKET_MAIN = STOCK_MARKET+"main/";


    public static void saveData(Activity activity, String nameShared, String key, String value) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(nameShared, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();

    }

    public static void putBoolean(Activity activity, String nameShared, String key, boolean value) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(nameShared, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();

    }


    public static <T> String fromObjectToString(T t) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(t);
    }


    public static User fromStringToUser(String string) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(string, User.class);

    }
    public static Task fromStringToTask(String string) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectReader objectReader = objectMapper.reader().withoutAttribute("typeTasks");

        return objectReader.readValue(string, Task.class);

    }



    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public static void clearData(Activity activity, String name) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(name,Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        return sharedPreferences.getString("token",null);

    }

    public static String getUserData(Context context ) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        return sharedPreferences.getString("userData",null);
    }

    public static String getUserDataFromData(Context context, String userData) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        return sharedPreferences.getString(userData,null);
    }
}
