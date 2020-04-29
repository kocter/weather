package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

            onBackPressed();

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


    public void onBackPressed() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        // Указываем Title
        alertDialog.setTitle("Выход");

        // Указываем текст сообщение
        alertDialog.setMessage("Вы уверены, что хотите выйти?");



        // Обработчик на нажатие ДА
        alertDialog.setPositiveButton("Таки да!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                finish();

            }
        });

        // Обработчик на нажатие НЕТ
        alertDialog.setNegativeButton("НЕТ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // показываем Alert
        alertDialog.show();
    }




}

