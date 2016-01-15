package com.bukit.mygeosystem.ModeYellowPage;

/**
 * Created by kevin on 12/10/2015.
 */
public class BusinessItem {

    String name;
    String address;
    String city;
    String km;
    Double lon;
    Double lat;

    public BusinessItem(String name, String address, String city, String km, Double lon, Double lat){
        this.name=name;
        this.address=address;
        this.city =city;
        this.km=km;
        this.lon=lon;
        this.lat=lat;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getCity(){
        return city;
    }

    public String getKm(){return km;}

    public Double getLon(){
        return lon;
    }

    public Double getLat(){
        return lat;
    }
}
