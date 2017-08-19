package com.example.swordpc07.world_weather_online;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by swordpc07 on 1/6/2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.CustomViewHolder> {
    Context context;
    private ArrayList<Weather_Getter_Setter> Mylist;


    WeatherAdapter(Context context , ArrayList<Weather_Getter_Setter> list){
        this.context = context;
        Mylist = list;
    }




    @Override
    public int getItemCount() {
        return (null != Mylist ? Mylist.size() : 0);
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_weather_detail, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        final Weather_Getter_Setter feedItem = SingeltonArrayList.addWeather.get(position);


        holder.value.setText(feedItem.getValue());
        holder.maxTemp.setText(feedItem.getMaxtemp());
        holder.minTemp.setText(feedItem.getMintemp());
        holder.huidity.setText(feedItem.getHumidity());
        holder.time.setText(feedItem.getTime());
        //holder.imageView.setImageBitmap(Picasso.with(context).load(feedItem.getImage_url()));
        Picasso.with(context).load(feedItem.getImage_url()).into(holder.imageView);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView value, minTemp, maxTemp, time, huidity ;

        ImageView imageView;

        public CustomViewHolder(View view) {
            super(view);

            this.value = (TextView) view.findViewById(R.id.value);
            this.maxTemp = (TextView) view.findViewById(R.id.maximum);
            this.minTemp = (TextView) view.findViewById(R.id.minimum);
            this.time = (TextView) view.findViewById(R.id.time_time);
            this.huidity = (TextView) view.findViewById(R.id.humididty);
            this.imageView = (ImageView) view.findViewById(R.id.image_url);

        }
    }
}
