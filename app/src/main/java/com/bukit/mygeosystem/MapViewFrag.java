package com.bukit.mygeosystem;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by kevin on 12/12/2015.
 */
public class MapViewFrag extends Fragment {

    Double myLon, myLat, desLon, desLat;
    String businessName;
    MapView myMapView;
    GoogleMap myMap;
    @Bind(R.id.mapoptions)
    RadioGroup options;

    final static int GLOBE_WIDTH = 256; // a constant in Google's map projection
    final static int ZOOM_MAX = 21;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        if(bundle!=null){
            myLat=bundle.getDouble("MYLAT");
            myLon=bundle.getDouble("MYLON");
            desLat=bundle.getDouble("DESLAT");
            desLon=bundle.getDouble("DESLON");
            businessName = bundle.getString("NAME");

        }else{
            Toast.makeText(getActivity(),"Can not get location, pleaes try again", Toast.LENGTH_SHORT).show();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_frag, container,false);
        myMapView = (MapView)v.findViewById(R.id.myMap);
        myMapView.onCreate(savedInstanceState);
        myMapView.onResume();
        myMap=myMapView.getMap();
        setupMap();
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

      options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId) {
              switch (checkedId) {
                  case (R.id.satelite):
                      myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                      break;
                  case (R.id.normal):
                      myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                      break;
                  case (R.id.hybridv):
                      myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                      break;
              }
          }
      });

    }

    private void setupMap(){
        myMap.setMyLocationEnabled(true);
        LatLng startLocation = new LatLng(myLat,myLon);
        LatLng endLocation = new LatLng(desLat,desLon);

        Marker m1 = myMap.addMarker(new MarkerOptions()
                .position(startLocation).title("you are here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Marker m2 = myMap.addMarker(new MarkerOptions()
                .position(endLocation).title(businessName)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));


        m1.showInfoWindow();
        m2.showInfoWindow();

       myMap.addPolyline(new PolylineOptions()
                .add(startLocation, endLocation)
                .width(6)
                .color(Color.BLUE).geodesic(true));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 14));



    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    public static int getBoundsZoomLevel(LatLng northeast,LatLng southwest,
                                         int width, int height) {
        double latFraction = (latRad(northeast.latitude) - latRad(southwest.latitude)) / Math.PI;
        double lngDiff = northeast.longitude - southwest.longitude;
        double lngFraction = ((lngDiff < 0) ? (lngDiff + 360) : lngDiff) / 360;
        double latZoom = zoom(height, GLOBE_WIDTH, latFraction);
        double lngZoom = zoom(width, GLOBE_WIDTH, lngFraction);
        double zoom = Math.min(Math.min(latZoom, lngZoom),ZOOM_MAX);
        return (int)(zoom);
    }
    private static double latRad(double lat) {
        double sin = Math.sin(lat * Math.PI / 180);
        double radX2 = Math.log((1 + sin) / (1 - sin)) / 2;
        return Math.max(Math.min(radX2, Math.PI), -Math.PI) / 2;
    }
    private static double zoom(double mapPx, double worldPx, double fraction) {
        final double LN2 = .693147180559945309417;
        return (Math.log(mapPx / worldPx / fraction) / LN2);
    }


}
