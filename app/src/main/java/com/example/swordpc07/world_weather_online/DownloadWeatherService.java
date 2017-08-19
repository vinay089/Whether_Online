package com.example.swordpc07.world_weather_online;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by swordpc07 on 1/6/2017.
 */

public class DownloadWeatherService extends IntentService {

    public String WEATHER_API = "http://api.worldweatheronline.com/premium/v1/weather.ashx?";

    public String API_KEY = "fecd9c48df6841609e9124048170501";

    LocationManager mLocationManager;
    double lat, longt;

    public DownloadWeatherService() {
        super("Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Location loc = getLastKnownLocation();

        if (loc != null) {
            lat = loc.getLatitude();
            longt = loc.getLongitude();
        }

        final String url = getUrl(lat, longt);
        Log.d("url = ", url);

        Object[] DataTransfer = new Object[1];

        DataTransfer[0] = url;


        new Timer().schedule(new TimerTask(){
            public void run() {

                new GetWeatherDetails().execute(url);

            }
        }, 0);



    }


    public String getUrl(double latitude, double lonngitutde) {

        String url = WEATHER_API + "key=" + API_KEY + "&num_of_days=1&tp=3&q=" + latitude + "," + lonngitutde + "&format=json&lang=hi";

        return url;
    }

    public Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(DownloadWeatherService.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DownloadWeatherService.this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    public class GetWeatherDetails extends AsyncTask<String, String, String> {

        String worldWeatherApi;

        String url;

        public GetWeatherDetails(){ }

        @Override
        protected String doInBackground(String... params) {
            try {
                //  Log.d("GetNearbyPlacesData", "doInBackground entered");

                url = (String) params[0];
                Log.d("Urlin execute = ", url);
                DownloadUrl downloadUrl = new DownloadUrl();
                worldWeatherApi = downloadUrl.readUrl(url);
                // Log.d("GooglePlacesReadTask", "doInBackground Exit");
            } catch (Exception e) {
                Log.d("GooglePlacesReadTask", e.toString());
            }

            return worldWeatherApi;
        }

        @Override
        protected void onPostExecute(String result) {
            // Log.d("GooglePlacesReadTask", "onPostExecute Entered");
//            final int chunkSize = 2048;
            /*for (int i = 0; i < result.length(); i += chunkSize) {
                Log.d("reault:", result.substring(i, Math.min(result.length(), i + chunkSize)));
            }*/
            WeatherParser sendData = new WeatherParser();
            try {
                sendData.dataParser(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent i = new Intent(DownloadWeatherService.this, Main2Activity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);


        }
    }

    public class WeatherParser {

        public WeatherParser() {
        }

        public void dataParser(String jsonData) throws JSONException {

            JSONObject jsonRootObject = new JSONObject(jsonData);

            //SingletonArrayList.all = new ArrayList<Question_Get_Set>();

            SingeltonArrayList.addWeather = new ArrayList<Weather_Getter_Setter>();
            JSONObject jsonObject = jsonRootObject.getJSONObject("data");

            /*JSONObject jsonweatherdata = jsonObject.getJSONObject("weather");*/

            JSONArray jsonArray = jsonObject.getJSONArray("weather");


            JSONObject jsonTemp = jsonArray.getJSONObject(0);

            String minTemp = jsonTemp.getString("mintempC");
            String maxTemp = jsonTemp.getString("maxtempC");
            String time=null , lang=null, weatherIcon = null, humidity=null;

            Log.d("Min Temperaur = " + minTemp, "Max TEmperature - " + maxTemp);

            JSONArray jsonHour = jsonTemp.getJSONArray("hourly");

            for (int j = 0; j < jsonHour.length(); j++) {
                JSONObject jObjt = jsonHour.getJSONObject(j);

                time = jObjt.getString("time");
                lang = (String) jObjt.getJSONArray("lang_hi").getJSONObject(0).getString("value");
                weatherIcon = jObjt.getJSONArray("weatherIconUrl").getJSONObject(0).getString("value");
                humidity= jObjt.getString("humidity");
                int tym = Integer.parseInt(time);

                switch(tym){
                    case 0: time = "12 AM"; break;
                    case 300: time = "3 AM"; break;
                    case 600: time = "6 AM" ; break;
                    case 900: time= "9 AM"; break;
                    case 1200: time = "12 PM" ;break;
                    case 1500: time= "3 PM"; break;
                    case 1800: time = "6 PM" ; break;
                    case 2100: time= "9 PM"; break;
                }

                Log.d("time = " + time + " & Humidity = " + humidity , "& language " + lang + " Weahet iCob " + weatherIcon);

                Weather_Getter_Setter weather_getter_setter = new Weather_Getter_Setter();

                weather_getter_setter.setMintemp(minTemp +  " \u00B0" + "C");
                weather_getter_setter.setMaxtemp(maxTemp +  " \u00B0" + "C");
                weather_getter_setter.setImage_url(weatherIcon);
                weather_getter_setter.setHumidity(humidity);
                weather_getter_setter.setValue(lang);
                weather_getter_setter.setTime(time);

                SingeltonArrayList.addWeather.add(weather_getter_setter);
            }


        }


    }
}
