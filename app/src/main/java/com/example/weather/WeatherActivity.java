package com.example.weather;



import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.kwabenaberko.openweathermaplib.constants.Lang;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.ThreeHourForecastCallback;
import com.kwabenaberko.openweathermaplib.models.threehourforecast.ThreeHourForecast;


public class WeatherActivity extends AppCompatActivity {

    private static  final int REQUEST_LOCATION=1;
    TextView WeatherInformation1,WeatherInformation2; //Здесь нужно придумать как выводить много элементов, пока для примера сделал 2
    LocationManager locationManager;
    String latitude,longitude;
    String[] Coords = new String[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        WeatherInformation1 = (TextView) findViewById(R.id.WeatherInformation1);
        WeatherInformation2 = (TextView) findViewById(R.id.WeatherInformation2);
        //Instantiate Class With Your ApiKey As The Parameter
        OpenWeatherMapHelper helper = new OpenWeatherMapHelper(getString(R.string.OPEN_WEATHER_MAP_API_KEY));

        //Set Units
        helper.setUnits(Units.METRIC);

        //Set lang
        helper.setLang(Lang.RUSSIAN);


        /*
        This Example Only Shows how to get current weather by city name
        Check the docs for more methods [https://github.com/KwabenBerko/OpenWeatherMap-Android-Library/]
        */

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Check gps is enable or not

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Write Function To enable gps

            OnGPS();
        } else {
//Подставить переменные на места получения координат
            Coords = getLocation();


            double latitude = Double.valueOf(Coords[0]);
            double longitude = Double.valueOf(Coords[1]);


///// Здесь получаем массив погоды и выводим из него данные в перменные
            helper.getThreeHourForecastByGeoCoordinates(latitude,longitude, new ThreeHourForecastCallback() {
                @Override
                public void onSuccess(ThreeHourForecast threeHourForecast) {

                    String City = threeHourForecast.getCity().getName() + "/" + threeHourForecast.getCity().getCountry();
                    String Time1 = threeHourForecast.getList().get(0).getDtTxt(); // Дата и Время
                    String Time2 = threeHourForecast.getList().get(1).getDtTxt(); // Дата и Время
                    Double Temperature1 = threeHourForecast.getList().get(0).getMain().getTemp(); // Температура
                    Double Temperature2 = threeHourForecast.getList().get(1).getMain().getTemp();// Температура
                    Double Wind1 = threeHourForecast.getList().get(0).getWind().getSpeed(); //Скорость ветра
                    Double Wind2= threeHourForecast.getList().get(1).getWind().getSpeed(); //Скорость ветра
                    String Weather1 = threeHourForecast.getList().get(0).getWeatherArray().get(0).getDescription();  // Описание погоды
                    String Weather2 = threeHourForecast.getList().get(1).getWeatherArray().get(0).getDescription(); // Описание погоды
                    Double WindDirection1 =threeHourForecast.getList().get(0).getWind().getDeg(); //Направление ветра
                    Double WindDirection2 =threeHourForecast.getList().get(1).getWind().getDeg(); //Направление ветра
                    long Pressure1 = Math.round(threeHourForecast.getList().get(0).getMain().getPressure()/1.33322); //Давление
                    long Pressure2 = Math.round(threeHourForecast.getList().get(1).getMain().getPressure()/1.33322);//Давление




// Производим вывод погоды
                    WeatherInformation1.setText("Город: " + City + "\n" + "Дата и Время: " + Time1 + "\n" +"На улице: " + Weather1 + "\n" + "Температура: " + Temperature1 + "\n" + "Скорость ветра: " + Wind1 + "\n" +"Направление ветра: " + WindDirection1+ "\n"+"Атмосферное давление: " + Pressure1);
                    WeatherInformation2.setText("Город: " + City + "\n" + "Дата и Время: " + Time2 + "\n" + "На улице: " + Weather2 + "\n" + "Температура: " + Temperature2 + "\n" + "Скорость ветра: " + Wind2 + "\n"+"Направление ветра: " + WindDirection2+ "\n"+"Атмосферное давление: " + Pressure2);


                }

                @Override
                public void onFailure(Throwable throwable) {
                    WeatherInformation1.setText("Пустовато здесь");
                    WeatherInformation2.setText("Пустовато и здесь");
                }


            });

        }

    }




    public void OnGPS() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

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
        final AlertDialog alertDialog=builder.create();
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

            if (ActivityCompat.checkSelfPermission(WeatherActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(WeatherActivity.this,

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
                }

                //Thats All Run Your App
            }
        }

        return Coords;

    }



}

