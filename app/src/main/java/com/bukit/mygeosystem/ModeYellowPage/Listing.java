package com.bukit.mygeosystem.ModeYellowPage;

/**
 * Created by kevin on 12/10/2015.
 */

public class Listing {


    private String id;

    private String name;

    private Address address;

    private GeoCode geoCode;

    private String distance;

    private String parentId;

    private Boolean isParent;

    private Content content;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Address getAddress() {
        return address;
    }


    public void setAddress(Address address) {
        this.address = address;
    }


    public GeoCode getGeoCode() {
        return geoCode;
    }


    public void setGeoCode(GeoCode geoCode) {
        this.geoCode = geoCode;
    }


    public String getDistance() {
        return distance;
    }


    public void setDistance(String distance) {
        this.distance = distance;
    }


    public String getParentId() {
        return parentId;
    }


    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


    public Boolean getIsParent() {
        return isParent;
    }


    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }


    public Content getContent() {
        return content;
    }


    public void setContent(Content content) {
        this.content = content;
    }

}