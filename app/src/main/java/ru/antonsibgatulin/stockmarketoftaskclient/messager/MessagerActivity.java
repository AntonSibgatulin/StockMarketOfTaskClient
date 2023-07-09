package ru.antonsibgatulin.stockmarketoftaskclient.messager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;

import ru.antonsibgatulin.stockmarketoftaskclient.R;
import ru.antonsibgatulin.stockmarketoftaskclient.adapters.MessagerAdapter;
import ru.antonsibgatulin.stockmarketoftaskclient.include.constant.Constant;
import ru.antonsibgatulin.stockmarketoftaskclient.include.dilog.Dilog;
import ru.antonsibgatulin.stockmarketoftaskclient.include.user.User;

public class MessagerActivity extends AppCompatActivity {

    private List<Dilog> dilogList;

    private ListView listView;
    private MessagerAdapter messagerAdapter;
    private User me;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messager);
        listView = findViewById(R.id.listView);
        dilogList = new ArrayList<>();

        try {
            me = Constant.fromStringToUser(getIntent().getStringExtra("me"));
            dilogList = new ArrayList<>();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        messagerAdapter = new MessagerAdapter(this, R.layout.fragment_message_preview, dilogList, me);
        listView.setAdapter(messagerAdapter);

    }
}