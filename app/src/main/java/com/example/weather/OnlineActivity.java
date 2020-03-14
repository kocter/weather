package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OnlineActivity extends AppCompatActivity {

    Button Gps;
    Button Weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        Gps =  findViewById(R.id.Gps);
        Weather =  findViewById(R.id.Weather);


    }

    public void Weather(View view) {
        // myTextView.setText("Все ок)");
        Intent Weather = new Intent(this, WeatherActivity.class);
        startActivity(Weather);
    }



}
