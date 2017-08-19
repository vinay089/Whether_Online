package com.example.swordpc07.world_weather_online;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class selectDate extends AppCompatActivity {

    Spinner select_lang_spinner;
    String selected_language, no_of_days, place;
    EditText place_text, no_of_days_text;
    int flag=0;

    CalendarView calendarView;

    String date_by_user= null;


    private String[] select_language={"English", "Arabic", "Bengali", "Bulgarian", "Chinese Simplified", "Chinese Traditional", "Czech",
            "Danish", "Dutch", "Finnish", "French",  "German",  "Greek", "Hindi",
            "Hungarian",
            "Italian",
            "Japanese",
            "Javanese",
            "Korean",
            "Mandarin",
            "Marathi",
            "Polish",
            "Portuguese",
            "Punjabi",
            "Romanian",
            "Russian",
            "Serbian",
            "Sinhalese",
            "Slovak",
            "Spanish",
            "Swedish",
            "Tamil",
            "Telugu",
            "Turkish",
            "Ukrainian",
            "Urdu",
            "Vietnamese",
            "Wu (Shanghainese)",
            "Xiang",
            "Yue (Cantonese)",
            "Zulu" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);

        select_lang_spinner= (Spinner) findViewById(R.id.spinner2);
        calendarView= (CalendarView) findViewById(R.id.calendarView4);
        Button onClick= (Button) findViewById(R.id.click);
        place_text= (EditText) findViewById(R.id.editText6);
        no_of_days_text= (EditText) findViewById(R.id.no_of_days_text);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(selectDate.this,
                android.R.layout.simple_spinner_item , select_language);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        select_lang_spinner.setAdapter(adapter);

        select_lang_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        selected_language= "en";
                        break;
                    case 1:
                        selected_language= "ar";
                        break;
                    case 2:
                        selected_language= "bn";
                        break;
                    case 3:
                        selected_language= "bg";
                        break;
                    case 4:
                        selected_language= "zh";
                        break;
                    case 5:
                        selected_language= "zh_tw";
                        break;
                    case 6:
                        selected_language= "cs";
                        break;
                    case 7:
                        selected_language= "da";
                        break;
                    case 8:
                        selected_language= "nl";
                        break;
                    case 9:
                        selected_language= "fi";
                        break;
                    case 10:
                        selected_language= "fr";
                        break;


                }
                Log.d("On clisked " , selected_language);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                month=month+1;
                String month_user= String.format("%02d", (month));

                date_by_user= (year+ "-"+ (month_user)+ "-"+ dayOfMonth).toString();
                Log.d("Sekected adeDate" , date_by_user );
            }

        });


    onClick.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            no_of_days = no_of_days_text.getText().toString();
            place = place_text.getText().toString();

            if (TextUtils.isEmpty(no_of_days) && TextUtils.isEmpty(place) && (TextUtils.isEmpty(date_by_user))) {
                Toast.makeText(selectDate.this, "Either date or field empty!!", Toast.LENGTH_LONG).show();

            } else {


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        Intent intent = new Intent(selectDate.this, DownloadDateUserService.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("date", date_by_user);
                        bundle.putString("no_of_days", no_of_days);
                        bundle.putString("language", selected_language);
                        bundle.putString("place", place);
                        intent.putExtras(bundle);
                        startService(intent);

                    }
                });
            }




            }
           /* if( (!isMyServiceRunning(DownloadDateUserService.class)) && (flag == 1)) {
                Log.d("service isn't running &" , " Button been clicked");
                startActivity(new Intent(selectDate.this, show_Date_Weather_Details.class));
            }*/

    });




    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
