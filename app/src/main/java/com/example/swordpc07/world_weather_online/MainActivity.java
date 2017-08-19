package com.example.swordpc07.world_weather_online;

/*  http://api.worldweatheronline.com/premium/v1/weather.ashx?
    key=fecd9c48df6841609e9124048170501&num_of_days=1&
    q=28.6967019,77.1306743&format=json&lang=hi  */

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.bugsee.library.Bugsee;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bugsee.launch(getApplication(), "dccd4c69-9be5-4acd-9d31-dc311ac11052");
        Bugsee.setEmail("vinayduderr@gmail.com");

        HashMap<String, Object> options = new HashMap<>();
        options.put(Bugsee.Option.NotificationBarTrigger, false);
        options.put(Bugsee.Option.ShakeToTrigger, true);
        options.put(Bugsee.Option.CrashReport, true);
        options.put(Bugsee.Option.VideoEnabled, false);
        Bugsee.launch(getApplication(), "dccd4c69-9be5-4acd-9d31-dc311ac11052", options);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (SingeltonArrayList.addWeather.size() == 0) ;
        {
            Log.d("length=", String.valueOf(SingeltonArrayList.addWeather.size()));

        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                startService(new Intent(MainActivity.this, DownloadWeatherService.class));
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setBackgroundColor(2);

            }


        });


    }
	
	private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


}


