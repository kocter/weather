package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.content.Context;
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {

    //private SharedPreferences sharedPrefs;
    Button Online;
    Button Write;
    Button Exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Online =  findViewById(R.id.Online);
        Write =  findViewById(R.id.Write);
        Exit =  findViewById(R.id.Exit);
    }
    public void Online(View view) {
       // myTextView.setText("Все ок)");
        Intent Online = new Intent(this, OnlineActivity.class);
        startActivity(Online);
    }

    public void List (View view) {
        //myTextView.setText("Все ок)");
        Intent List = new Intent(this, ListActivity.class);
        startActivity(List);
    }

    public void Exit(View view) {
        System.exit(0);
    }

    public void GPS(View view) {
        // myTextView.setText("Все ок)");
        Intent GPS = new Intent(this, GpsActivity.class);
        startActivity(GPS);
    }

    public void Weather(View view) {
        // myTextView.setText("Все ок)");
        Intent Weather = new Intent(this, WeatherActivity.class);
        startActivity(Weather);
    }



}
