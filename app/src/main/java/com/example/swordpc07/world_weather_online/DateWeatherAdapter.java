package com.example.swordpc07.world_weather_online;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by swordpc07 on 1/17/2017.
 */

public class DateWeatherAdapter extends RecyclerView.Adapter<DateWeatherAdapter.MyCustomViewHolder> {
    Context context;
    private ArrayList<Weather_Getter_Setter> Mylist;


    DateWeatherAdapter(Context context, ArrayList<Weather_Getter_Setter> list) {
        this.context = context;
        Mylist = list;
    }


    @Override
    public int getItemCount() {
        return (null != Mylist ? Mylist.size() : 0);
    }


    @Override
    public DateWeatherAdapter.MyCustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_specific_day, null);
        DateWeatherAdapter.MyCustomViewHolder viewHolder = new DateWeatherAdapter.MyCustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyCustomViewHolder holder, int position) {

        final Weather_Getter_Setter feedItem = SingeltonArrayList.userDateWeather.get(position);

        String setMinimum= feedItem.getMintemp() + "|" + feedItem.getMinTempF();
        String setMaxmum= feedItem.getMaxtemp() + "|" + feedItem.getMaxTempF();

        holder.date_choose.setText(feedItem.getDate());
        holder.mintemp.setText(setMinimum);
        holder.maxtemp.setText(setMaxmum);
        holder.sunrise.setText(feedItem.getSunrise());
        holder.sunset.setText(feedItem.getSunset());
        holder.moonrise.setText(feedItem.getMoonrise());
        holder.moonset.setText(feedItem.getMoonset());

    }


    class MyCustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView mintemp, maxtemp, sunrise, sunset, moonrise, moonset, date_choose;

        ImageView imageView;

        public MyCustomViewHolder(View view) {
            super(view);

            this.mintemp = (TextView) view.findViewById(R.id.mintemp);
            this.maxtemp = (TextView) view.findViewById(R.id.maxtemp);
            this.sunrise = (TextView) view.findViewById(R.id.sunrise);
            this.sunset = (TextView) view.findViewById(R.id.sunset);
            this.moonrise = (TextView) view.findViewById(R.id.moonrise);
            this.moonset = (TextView) view.findViewById(R.id.moonset);
            this.date_choose= (TextView) view.findViewById(R.id.datechoose);

        }
    }
}
