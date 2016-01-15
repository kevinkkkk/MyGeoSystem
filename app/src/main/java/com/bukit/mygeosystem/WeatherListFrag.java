package com.bukit.mygeosystem;



import android.app.Fragment;
import android.content.Context;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.bukit.mygeosystem.ModeWeather.FiveDaysForecast;


import com.bukit.mygeosystem.ModeWeather.WeatherList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import java.util.List;


import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by kevin on 12/8/2015.
 */
public class WeatherListFrag extends Fragment implements WeatherView<FiveDaysForecast>{

    WeatherListPresenter presenter;
    @Bind(R.id.myList) ListView forecastListView;
    //Typeface weatherFonts;
    ArrayList<WeatherList> weatherLists = new ArrayList();
    WeatherListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new WeatherListPresenter(this);
        //weatherFonts=((MainActivity)getActivity()).providerWeatherFont();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weather_list_view_frag, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        adapter = new WeatherListAdapter(weatherLists, getActivity());
        forecastListView.setAdapter(adapter);
        presenter.loadData();
    }

    @Override
    public void setData(FiveDaysForecast w) {

        List<com.bukit.mygeosystem.ModeWeather.List> forecastList = w.getList();
        for (int i =0; i<forecastList.size(); i++){
            com.bukit.mygeosystem.ModeWeather.List one=forecastList.get(i);
            String date = one.getDtTxt().substring(5);
            String icon = WeatherFrag.IMAGE_URL+one.getWeather().get(0).getIcon()+".png";
            WeatherList item = new WeatherList(date,one.getMain().getTempMax(),one.getMain().getTempMin(),
                    icon,one.getWind().getSpeed());

            weatherLists.add(item);
        }

        adapter.notifyDataSetChanged();


    }

    @Override
    public void showError() {
        for (int i=0; i<1; i++){
            WeatherList item = new WeatherList("error", 0.0,0.0,"error", 0.0);
            weatherLists.add(item);
        }

    }

   /* private String getWeatherIcon(int actualId){
        int id = actualId/100;
        String icon = "";
        if (actualId==800) {

                icon = getActivity().getString(R.string.weather_sunny);

        }
        else{
            switch (id){
                case 2 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 3 : icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 7 : icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 8 : icon = getActivity().getString(R.string.weather_cloudy);
                    break;
                case 6 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 5 : icon = getActivity().getString(R.string.weather_rainy);
                    break;
            }
        }
        return icon;
    }*/

    //----------------------------------------------------------------------------------------------

    class WeatherListAdapter extends ArrayAdapter<WeatherList>{

        class ViewHolder{
            TextView day;
            ImageView icon;
            TextView temp_hi;
            TextView temp_low;
            TextView windSpeed;

            ViewHolder(View v){
                day = (TextView) v.findViewById(R.id.date);
                icon = (ImageView) v.findViewById(R.id.weatherIcon);
                temp_hi = (TextView) v.findViewById(R.id.temperture);
                temp_low = (TextView) v.findViewById(R.id.lowerTemp);
                windSpeed = (TextView) v.findViewById(R.id.wind);
                //icon.setTypeface(weatherFonts);
            }
        }

        public WeatherListAdapter(ArrayList<WeatherList> list, Context c){
            super(c, R.layout.item_in_list,R.id.date, list);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row=super.getView(position,convertView,parent);
            ViewHolder holder = (ViewHolder) row.getTag();

            if (holder==null){
                holder = new ViewHolder(row);
                row.setTag(holder);
            }
            WeatherList item = getItem(position);
            holder.day.setText(item.getDate());
            Picasso.with(getActivity()).load(item.getWeatherIcon()).into(holder.icon);
            //holder.icon.setText(item.getWeatherIcon());
            holder.temp_hi.setText(String.format("%.1f",item.getTemp_max())+"℃");
            holder.temp_low.setText(String.format("%.1f",item.getTemp_low())+"℃");
            holder.windSpeed.setText(String.format("%.1f", item.getWindSpeed())+"km");

            return row;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
