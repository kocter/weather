package com.example.weather;



import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kwabenaberko.openweathermaplib.constants.Lang;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.ThreeHourForecastCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;
import com.kwabenaberko.openweathermaplib.models.threehourforecast.ThreeHourForecast;

public class NewActivity extends AppCompatActivity {


    TextView Str;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        Str = (TextView) findViewById(R.id.Str);
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

//Подставить переменные на места получения координат
        helper.getThreeHourForecastByGeoCoordinates(58.013144,56.234039, new ThreeHourForecastCallback() {
            @Override
            public void onSuccess(ThreeHourForecast threeHourForecast) {

                String City = threeHourForecast.getCity().getName() + "/" + threeHourForecast.getCity().getCountry();
                Double Temperature = threeHourForecast.getList().get(0).getMain().getTempMax();
                Double Wind = threeHourForecast.getList().get(0).getWind().getSpeed();
                String Weather = threeHourForecast.getList().get(0).getWeatherArray().get(0).getDescription();




                Str.setText("Город: " + City+"\n" +"На улице: " + Weather+"\n" +"Температура: " + Temperature+"\n" + "Скорость ветра: " + Wind+"\n"
                );


            }

            @Override
            public void onFailure(Throwable throwable) {
                Str.setText("Пустовато здесь");
            }




        });

    }
}

