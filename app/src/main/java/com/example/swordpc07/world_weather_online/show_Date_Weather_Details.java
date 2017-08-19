package com.example.swordpc07.world_weather_online;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

public class show_Date_Weather_Details extends AppCompatActivity {

    RecyclerView mRecyclerView;
    TextView cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__date__weather__details);

        Log.d("mfhfhf", "in third activty");
        cityName= (TextView) findViewById(R.id.city_name);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DateWeatherAdapter adapter= new DateWeatherAdapter(show_Date_Weather_Details.this, SingeltonArrayList.userDateWeather);

        mRecyclerView.setAdapter(adapter);

        Weather_Getter_Setter weather_city_name = SingeltonArrayList.cityName.get(0);
        cityName.setText(weather_city_name.getCity());
        Log.d("City Name=" , weather_city_name.getCity());

        for(int k=0; k<SingeltonArrayList.cityName.size(); k++){
            Log.d("City =", SingeltonArrayList.cityName.get(k).getCity());
        }

    }
}
