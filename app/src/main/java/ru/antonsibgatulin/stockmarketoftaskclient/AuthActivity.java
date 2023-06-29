package ru.antonsibgatulin.stockmarketoftaskclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.antonsibgatulin.stockmarketoftaskclient.fragments.SignInFragment;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        getSupportFragmentManager().beginTransaction().add(R.id.frameAuthContiner,new SignInFragment()).commit();

    }
}