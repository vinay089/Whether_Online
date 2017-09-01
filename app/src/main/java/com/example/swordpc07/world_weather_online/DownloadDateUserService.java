package com.example.swordpc07.world_weather_online;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by swordpc07 on 1/17/2017.
 */

public class DownloadDateUserService extends IntentService {
    String place, no_of_days, date, language;
    public String WEATHER_API = "http://api.worldweatheronline.com/premium/v1/weather.ashx?";

    public String API_KEY = "key=da98314d6a16444e84d42542172908&tp=12&q=";

    String url_api;
    int FLAG=0;

    public DownloadDateUserService(){
        super("Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle b= intent.getExtras();

        place= b.getString("place");
        no_of_days= b.getString("no_of_days");
        date= b.getString("date");
        language= b.getString("language");

        Log.d(date + " " + no_of_days, place + " "+ language);
        //"http://api.worldweatheronline.com/premium/v1/weather.ashx?key=fecd9c48df6841609e9124048170501&tp=12&q=shimla&format=xml&lang=en"
        String place_without_spaces= place.replaceAll("\\s", "+").toLowerCase();

        url_api= WEATHER_API+API_KEY+place_without_spaces+"&format=json&lang=en"/*+language*/;
        Log.d("url=",url_api);

        new Timer().schedule(new TimerTask(){
            public void run() {

                new GetWeatherDataDetails().execute(url_api);

            }
        }, 0);


    }

    public class GetWeatherDataDetails extends AsyncTask<String, String, String> {

        String worldWeatherApi;

        String url;

        public GetWeatherDataDetails(){ }


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

            try {
                dataParser(worldWeatherApi);
                return "done";
            } catch (JSONException e) {
                e.printStackTrace();
            }
           return "error";
        }

        @Override
        protected void onPostExecute(String result) {
            // Log.d("GooglePlacesReadTask", "onPostExecute Entered");
            /*final int chunkSize = 2048;
            for (int i = 0; i < result.length(); i += chunkSize) {
                Log.d("reault:", result.substring(i, Math.min(result.length(), i + chunkSize)));
            }*/

            switch (result) {

                case "done":
                    Intent i = new Intent(DownloadDateUserService.this, show_Date_Weather_Details.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    break;

                case "error":

                    Toast.makeText(getBaseContext(), "Some error occur.", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

    public void dataParser(String jsonData) throws JSONException {

        SingeltonArrayList.userDateWeather= new ArrayList<Weather_Getter_Setter>();
        SingeltonArrayList.cityName= new ArrayList<Weather_Getter_Setter>();

        Weather_Getter_Setter get_set= new Weather_Getter_Setter();
        int count=0;

        JSONObject jsonRootObject= new JSONObject(jsonData);

        JSONObject jsonObject= jsonRootObject.getJSONObject("data");

        JSONArray jArray= jsonObject.getJSONArray("request");
        String city_name= jArray.getJSONObject(0).getString("query");

        Log.d("QUery", city_name);

        get_set.setCity(city_name);

        SingeltonArrayList.cityName.add(get_set);

        JSONArray jsonRootArray= jsonObject.getJSONArray("weather");

        for(int i=0; i<jsonRootArray.length(); i++) {

            JSONObject jsonObject1 = (JSONObject) jsonRootArray.get(i);
            //Log.d(i+".)", String.valueOf(jsonObject1));


            String date_user_check = jsonObject1.getString("date");
            Log.d("dateuserchcke", date_user_check);


            if (date_user_check.equals(date) || ( (FLAG==1) && (count < Integer.parseInt(no_of_days)) )) {

                FLAG=1;
                count++;
                String date_user = jsonObject1.getString("date");
                String MaxTemp = jsonObject1.getString("maxtempC");
                String MinTemp = jsonObject1.getString("mintempC");
                String MinTempF = jsonObject1.getString("mintempF");
                String MaxTempF = jsonObject1.getString("maxtempF");

                JSONArray jsonArray = jsonObject1.getJSONArray("astronomy");
                JSONObject jsonObjectAstrnomy = (JSONObject) jsonArray.get(0);

                String sunrise = jsonObjectAstrnomy.getString("sunrise");
                String sunset = jsonObjectAstrnomy.getString("sunset");
                String moonrise = jsonObjectAstrnomy.getString("moonrise");
                String moonset = jsonObjectAstrnomy.getString("moonset");



                Weather_Getter_Setter weather_getter_setter = new Weather_Getter_Setter();

                weather_getter_setter.setDate(date_user);
                weather_getter_setter.setMintemp(MinTemp + "°" +"C");
                weather_getter_setter.setMinTempF(MinTempF+ "°" +"F");
                weather_getter_setter.setMaxtemp(MaxTemp+ "°" +"C");
                weather_getter_setter.setMaxTempF(MaxTempF+ "°" +"F");
                weather_getter_setter.setSunrise(sunrise);
                weather_getter_setter.setSunset(sunset);
                weather_getter_setter.setMoonrise(moonrise);
                weather_getter_setter.setMoonset(moonset);

                SingeltonArrayList.userDateWeather.add(weather_getter_setter);

                Log.d((i + 1) + ") date=" + date_user, MinTemp + "|" + MinTempF + " && " + MaxTemp + "|" + MaxTempF);


            }

        }

    }
}
