package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //private SharedPreferences sharedPrefs;
    Button Online;
    Button Write;
    Button Exit;
    Button Weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Online = findViewById(R.id.Online);
        Write = findViewById(R.id.Write);
        Exit = findViewById(R.id.Exit);
        Weather = findViewById(R.id.Weather);
        if (!isOnline()) {
            Toast.makeText(this, "Нет интернет соединения", Toast.LENGTH_LONG).show();
            Online.setEnabled(false);
            Weather.setEnabled(false);


             }
        }

        public void Online (View view){
            // myTextView.setText("Все ок)");
            Intent Online = new Intent(this, OnlineActivity.class);
            startActivity(Online);
        }

        public void List (View view){
            //myTextView.setText("Все ок)");
            Intent List = new Intent(this, ListActivity.class);
            startActivity(List);
        }


        public void Weather (View view){
            // myTextView.setText("Все ок)");
            Intent Weather = new Intent(this, WeatherActivity.class);
            startActivity(Weather);
        }


        public void Exit (View view){
            System.exit(0);
        }
        protected boolean isOnline()
    {
            String cs = Context.CONNECTIVITY_SERVICE;
            ConnectivityManager cm = (ConnectivityManager) getSystemService(cs);
            if (cm.getActiveNetworkInfo() == null) {
                return false;
            } else {
                return true;
            }
        }



}

