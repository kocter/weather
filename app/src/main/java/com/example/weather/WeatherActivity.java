package com.example.weather;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {


    //GPS gps= new GPS();

    /**
     * Define the OpenWeatherMap API URL
     */
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?lat=58.013144&lon=56.234039&appid=d7db4e9fe6ae9bb5075240153a655c12";

    /**
     * Instance variables to represent the "London Current Weather Synchronously"
     * and "London Current Weather Asynchronously" buttons,
     * "Temperature", "Pressure" and "Humidity" TextViews and loadingProgressbar.
     */
    private Button getCurrentWeatherAsync;
    private TextView temperatureTextView, pressureTextView, humidityTextView, nameTextView,feels_likeTextView,wind_speedTextView,wind_directionTextView,
            description_weatherTextView,gpsTextView;
    private ProgressBar loadingProgressBar;
    private LinearLayout getLondonCurrentWeatherLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        /**
         * Instantiate the variables we declared above using the ID values
         * we specified in the layout XML file.
         */

        getCurrentWeatherAsync = (Button) findViewById(R.id.get_weather_async_btn);
        getLondonCurrentWeatherLinearLayout = (LinearLayout) findViewById(R.id.root_ll);
        temperatureTextView = (TextView) findViewById(R.id.temperature_tv);
        feels_likeTextView = (TextView) findViewById(R.id.feels_like_tv);
        pressureTextView = (TextView) findViewById(R.id.pressure_tv);
        humidityTextView = (TextView) findViewById(R.id.humidity_tv);
        nameTextView = (TextView) findViewById(R.id.name_tv);
        wind_speedTextView = (TextView) findViewById(R.id.wind_speed_tv);
        wind_directionTextView = (TextView) findViewById(R.id.wind_direction_tv);
//        description_weatherTextView = (TextView) findViewById(R.id.activity_weather_description_weather_tv);
        gpsTextView = (TextView) findViewById(R.id.activity_GPS_tv);
        loadingProgressBar = (ProgressBar) findViewById(R.id.pb);

        /**
         * Add a listener to getCurrentWeatherAsync so that we can handle presses.
         */

        getCurrentWeatherAsync.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        /**
         * make an asynchronous background request.
         */

        getWeatherAsync();

    }


    /**
     * getWeatherAsync() method will make an asynchronous background request
     * by using OkHttpClient class, Request main class and Callback interface.
     */
    private void getWeatherAsync() {
        getLondonCurrentWeatherLinearLayout.setVisibility(View.INVISIBLE);
        loadingProgressBar.setVisibility(View.VISIBLE);

        /**
         * To make REST API call through Android OkHttp Library we may first need to build an instance of OkHttpClient class
         * and also an instance of Request class. Since Request class is the main class of OkHttp Library which executes
         * all the requests.
         */
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL)
                .build();
        /**
         * After this, call enqueue() method to make an asynchronous API request, and implement inside it
         * CallBack Interface Listener "Observer" since this Callback Interface has to methods onResponse()
         * that is fire once a successive response is returned from OpenWeatherMap API and onFailure()
         * that is fire once an error occurs
         */
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingProgressBar.setVisibility(View.GONE);
                        Toast.makeText(WeatherActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseString = response.body().string();
                    /**
                     * Parse JSON response to Gson library
                     */
                    JSONObject jsonObject = new JSONObject(responseString);
                    Gson gson = new Gson();
                    final WeatherDataBean weatherDataBean = gson.fromJson(jsonObject.toString(), WeatherDataBean.class);
                    /**
                     * Any action involving the user interface must be done in the main or UI thread, using runOnUiThread()
                     * method will run this specified action on the UI thread.
                     */
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateUI(weatherDataBean);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    /**
     * updateUI() method will be used once a successive response is returned from OpenWeatherMap API
     *
     * @param weatherDataBean that is returned from successive OpenWeatherMap API request
     */
    private void updateUI(WeatherDataBean weatherDataBean) {
        loadingProgressBar.setVisibility(View.GONE);
        if (weatherDataBean != null) {
            getLondonCurrentWeatherLinearLayout.setVisibility(View.VISIBLE);
//            gpsTextView.setText("Координаты : " + gps);
            nameTextView.setText("You are in : " + weatherDataBean.name);
            temperatureTextView.setText("Temperature : " + weatherDataBean.getMain().getTemp() + " Celsius");
            feels_likeTextView.setText("Feels like : " + weatherDataBean.getMain().getFeels_like());
            pressureTextView.setText("Pressure : " + weatherDataBean.getMain().getPressure());
            humidityTextView.setText("Humidity : " + weatherDataBean.getMain().getHumidity());
            wind_speedTextView.setText("Wind speed : " + weatherDataBean.getWind().getSpeed());
            wind_directionTextView.setText("Wind direction : " + weatherDataBean.getWind().getDeg());

//            description_weatherTextView.setText("Description weather : " + weatherDataBean.weather);
        }
    }


}