package com.bukit.mygeosystem;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bukit.mygeosystem.ModeWeather.OpenWeather;
import com.squareup.picasso.Picasso;


import java.text.DateFormat;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kevin on 12/6/2015.
 */


public class WeatherFrag extends Fragment implements  WeatherView<OpenWeather>{

    public static final String IMAGE_URL="http://openweathermap.org/img/w/";

    @Bind(R.id.city_field) TextView cityName;
    @Bind(R.id.updated_field) TextView updatedInfo;
    @Bind(R.id.weather_icon) ImageView weatherIcon;
    @Bind(R.id.current_temperature_field) TextView temperature;
    @Bind(R.id.details_field) TextView details;
    @Bind(R.id.hi_low) TextView low_high;
    @Bind(R.id.sun) TextView sun;
    //a new things

    WeatherPresenter presenter;
    //Typeface weatherFont;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //weatherFont = ((MainActivity)getActivity()).providerWeatherFont();
        presenter = new WeatherPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.weather_frag, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
       // weatherIcon.setTypeface(weatherFont);
        presenter.loadData();
    }

    @Override
    public void setData(OpenWeather w) {
        try {
            cityName.setText("Wind speed: " + w.getWind().getSpeed() + " km/h");

            details.setText(w.getWeather().get(0).getDescription().toUpperCase(Locale.CANADA)
                    + "\n" + "Humidity: " + w.getMain().getHumidity() + "%"
                    + "\n" + "Pressure: " + w.getMain().getPressure() + " hpa");

            temperature.setText(String.format("%.2f", w.getMain().getTemp()) + " ℃");

            DateFormat currentDate = DateFormat.getDateTimeInstance();
            String updatedTime = currentDate.format(new Date(w.getDt() * 1000));
            updatedInfo.setText(updatedTime);

            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            long sunRiseTime = new Date(w.getSys().getSunrise() * 1000).getTime();
            long sunSetTime = new Date(w.getSys().getSunset() * 1000).getTime();
            String sunR = timeFormat.format(sunRiseTime);
            String sunS = timeFormat.format(sunSetTime);
            String sunInfo = "Sunrise " + sunR + "\n" + "Sunset " + sunS;
            sun.setText(sunInfo);

            low_high.setText("Highest: " + w.getMain().getTempMax() + " ℃" + "  Lowest: " + w.getMain().getTempMin() + " ℃");

            setWeatherIconViaPicasso(w.getWeather().get(0).getIcon());
        }catch (NullPointerException e){

        }

    }

    private void setWeatherIconViaPicasso(String icon){
        String url = IMAGE_URL+icon+".png";
        Picasso.with(getActivity()).load(url).into(weatherIcon);
    }

   /* private void setWeatherIcon(int actualId, Long sunrise, long sunset){
        int id = actualId/100;
        String icon = "";
        if (actualId==800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
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
        weatherIcon.setText(icon);
    }*/

    @Override
    public void showError() {

        //cityName.setText("Data error!!!");

    }

    @OnClick(R.id.more) void toListFrag(){
        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.myContainer, new WeatherListFrag()).commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
