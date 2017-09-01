package com.example.swordpc07.world_weather_online;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by swordpc07 on 8/29/2017.
 */

public class AsyncTaskToFetchWhetherDetails {

    public static class GetWeatherDetails extends AsyncTask<String, String, String> {

        String worldWeatherApi;
        String url;
        ProgressDialog loading;
        Context context;

        public GetWeatherDetails(Context context){
            this.context = context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(context, "Please Wait", null, true, true);
        }

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

//            DownloadWeatherService.WeatherParser sendData = new DownloadWeatherService.WeatherParser();
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
//            final int chunkSize = 2048;
            /*for (int i = 0; i < result.length(); i += chunkSize) {
                Log.d("reault:", result.substring(i, Math.min(result.length(), i + chunkSize)));
            }*/

            switch (result) {

                case "done":
                    loading.dismiss();

                    Intent i = new Intent(context, Main2Activity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    break;

                case "error":
                    loading.dismiss();
                    Toast.makeText(context, "Some error occur.!", Toast.LENGTH_LONG).show();
                    break;

            }
        }
    }

    public static void dataParser(String jsonData) throws JSONException {

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
