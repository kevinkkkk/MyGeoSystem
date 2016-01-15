package com.bukit.mygeosystem;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;



import android.os.IBinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bukit.mygeosystem.Dagger.DaggerMainActivityComponet;
import com.bukit.mygeosystem.Dagger.MainActivityComponet;
import com.bukit.mygeosystem.Dagger.MainActivityModule;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.gms.maps.model.LatLng;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;

public class MainActivity extends AppCompatActivity implements BusinessListFrag.OnVendorSelectedListener{

    @Bind (R.id.locationInfo) TextView myLocationInfo;

    @Bind (R.id.getStreetName) Button getStreetName;

    @Bind (R.id.findWeather) Button findWeather;

    @Bind (R.id.findTitle) Spinner findTitle;

    @Bind(R.id.locationCoordinate) TextView longLatitude;

    @Bind(R.id.donut_progress) DonutProgress donutProgress;

    @Bind(R.id.myContainer) ViewGroup container;

    Location myLocation;

    FetchGeoInfo.ServerBinder binder;

    LocationManager manager;

    LocationListener myLocationListener;

    public static MainActivityComponet mainActivityComponet;



    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (FetchGeoInfo.ServerBinder)service;

            String myAddress=binder.getLocationInfo(myLocation);
            myLocationInfo.setText(myAddress);
            findTitle.setVisibility(View.VISIBLE);
            findWeather.setVisibility(View.VISIBLE);
            container.setVisibility(View.VISIBLE);

            injectDJ();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }



    private void setupMyLocation(){
        donutProgress.setProgress(100);
        donutProgress.setVisibility(View.VISIBLE);
        donutProgress.startAnimation(new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
            }
        });
        manager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationProvider = LocationManager.GPS_PROVIDER;
        }

        myLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                myLocation = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        manager.requestLocationUpdates(locationProvider, 1000 * 60 * 2, 0, myLocationListener);

        Toast.makeText(getApplicationContext(), "Working on to find out your location...", Toast.LENGTH_SHORT).show();

        while (myLocation==null){
            myLocation = manager.getLastKnownLocation(locationProvider);

        }

        donutProgress.setVisibility(View.GONE);
        longLatitude.setVisibility(View.VISIBLE);
        longLatitude.setText(String.format("%.2f", myLocation.getLongitude()) + " "
                + String.format("%.2f", myLocation.getLatitude())
                + " " + String.format("%.2f", myLocation.getAltitude()) + "m");

        findTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String)findTitle.getSelectedItem();
                //Toast.makeText(getApplicationContext(),selected,Toast.LENGTH_SHORT).show();
                Bundle bundle=new Bundle();
                bundle.putString("Selected", selected);
                bundle.putDouble("LAT",myLocation.getLatitude());
                bundle.putDouble("LON", myLocation.getLongitude());
                BusinessListFrag businessListFrag = new BusinessListFrag();
                businessListFrag.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.myContainer, businessListFrag).commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public Location getMyLocation(){
        return myLocation;
    }
    public FetchGeoInfo.ServerBinder getBinder(){return binder;}


    @OnClick (R.id.getStreetName) void getMyAddress(){
        setupMyLocation();
        bindService(new Intent(this, FetchGeoInfo.class), serviceConnection, BIND_AUTO_CREATE);

    }

    public void injectDJ(){
        //initial DJ
        mainActivityComponet = DaggerMainActivityComponet.builder().mainActivityModule(new MainActivityModule(this)).build();
    }




    @OnClick (R.id.findWeather) void transitToWeahtherFrag(){
        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.myContainer, new WeatherFrag()).commit();

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

        manager.removeUpdates(myLocationListener);

        try {
            unbindService(serviceConnection);
        }catch (Exception e){
            Toast.makeText(getApplication(),e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Typeface providerWeatherFont() {
        return Typeface.createFromAsset(getAssets(), "fonts/weathericon.ttf");
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if(fm.getBackStackEntryCount()>0){
            fm.popBackStack();
        }else {

            super.onBackPressed();
        }
    }

    @Override
    public void goToMapView(Bundle bundle) {
        MapViewFrag mapViewFrag = new MapViewFrag();
        mapViewFrag.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.myContainer, mapViewFrag).commit();
    }





}
