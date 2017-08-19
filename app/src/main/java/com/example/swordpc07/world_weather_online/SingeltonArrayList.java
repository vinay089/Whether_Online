package com.example.swordpc07.world_weather_online;

import java.util.ArrayList;

/**
 * Created by swordpc07 on 1/6/2017.
 */

public class SingeltonArrayList {

    public static ArrayList<Weather_Getter_Setter> addWeather = new ArrayList<Weather_Getter_Setter>();
    public static ArrayList<Weather_Getter_Setter> userDateWeather= new ArrayList<Weather_Getter_Setter>();
    public static ArrayList<Weather_Getter_Setter> cityName= new ArrayList<Weather_Getter_Setter>();

    private static SingeltonArrayList myObj;

    public SingeltonArrayList(){ }


    public static SingeltonArrayList getInstance(){
        if(myObj == null)
            myObj = new SingeltonArrayList();

        return myObj;
    }



}
