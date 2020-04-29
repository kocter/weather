package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static  final int REQUEST_LOCATION=1;
    LocationManager locationManager;
    String latitude,longitude;
    String[] Coords = new String[2];




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




        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Check gps is enable or not








        }

        public void Online (View view){
            // myTextView.setText("Все ок)");
            Intent Online = new Intent(this, OnlineActivity.class);

//////Проверка на наличие интернета
            if (!isOnline()) {
                Toast.makeText(this, "Нет интернет соединения", Toast.LENGTH_LONG).show();




            }
            else {

//////Проверка на наличие получение координат
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //Write Function To enable gps

                    OnGPS();
                } else {

                    //Проверка полгрузился ли GPS
                    Coords = getLocation();
                    double latitude = Double.valueOf(Coords[0]);
                    double longitude = Double.valueOf(Coords[1]);
                    if (latitude == 0) {

                        Toast.makeText(this, "Gps еще не загрузился", Toast.LENGTH_SHORT).show();


                    } else {
                        startActivity(Online);
                    }
                }




            }
            }





        public void List (View view){
            //myTextView.setText("Все ок)");
            Intent List = new Intent(this, ListActivity.class);
            startActivity(List);
        }


        public void Weather (View view){
            // myTextView.setText("Все ок)");
            Intent Weather = new Intent(this, WeatherActivity.class);


            //////Проверка на наличие интернета
            if (!isOnline()) {
                Toast.makeText(this, "Нет интернет соединения", Toast.LENGTH_LONG).show();




            }
            else {

//////Проверка на наличие получение координат
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //Write Function To enable gps

                    OnGPS();
                } else {

                    //Проверка полгрузился ли GPS
                    Coords = getLocation();
                    double latitude = Double.valueOf(Coords[0]);
                    double longitude = Double.valueOf(Coords[1]);
                    if (latitude == 0) {

                        Toast.makeText(this, "Gps еще не загрузился", Toast.LENGTH_SHORT).show();


                    } else {
                        startActivity(Weather);
                    }
                }




            }








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

    public String[] getLocation() {



        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Check gps is enable or not

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            //Write Function To enable gps

            OnGPS();
        }

        else
        {
            //Check Permissions again

            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,

                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            }
            else {
                Location LocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Location LocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                Location LocationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                if (LocationGps != null) {
                    double lat = LocationGps.getLatitude();
                    double longi = LocationGps.getLongitude();
                    String lat1 = String.format("%.6f", lat);
                    String longi1 = String.format("%.6f", longi);

                    latitude = String.valueOf(lat1).replace(',', '.');
                    longitude = String.valueOf(longi1).replace(',', '.');

                    // WeatherInformation1=("lat="+latitude+"&lon="+longitude).replace(',', '.');
                    Coords[0]=latitude;
                    Coords[1]=longitude;

                } else if (LocationNetwork != null) {
                    double lat = LocationNetwork.getLatitude();
                    double longi = LocationNetwork.getLongitude();
                    String lat1 = String.format("%.6f", lat);
                    String longi1 = String.format("%.6f", longi);

                    latitude = String.valueOf(lat1).replace(',', '.');
                    longitude = String.valueOf(longi1).replace(',', '.');
                    //    WeatherInformation1=("lat="+latitude+"&lon="+longitude).replace(',', '.');
                    Coords[0]=latitude;
                    Coords[1]=longitude;


                } else if (LocationPassive != null) {


                    double lat = LocationPassive.getLatitude();
                    double longi = LocationPassive.getLongitude();


                    String lat1 = String.format("%.6f", lat);
                    String longi1 = String.format("%.6f", longi);
                    latitude = String.valueOf(lat1).replace(',', '.');
                    longitude = String.valueOf(longi1).replace(',', '.');
                    Coords[0]=latitude;
                    Coords[1]=longitude;



                } else {
                    Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
                    Coords[0]="0";
                    Coords[1]="0";
                }

                //Thats All Run Your App
            }
        }

        return Coords;

    }

    public void OnGPS() {

        final androidx.appcompat.app.AlertDialog.Builder builder= new androidx.appcompat.app.AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final androidx.appcompat.app.AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }




}

