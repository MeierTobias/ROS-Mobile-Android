package com.schneewittchen.rosandroid.widgets.djinavsat;

public class DjiNavSatUpdateEvent {
    public double wgs84_long;
    public double wgs84_lat;
    public double wgs84_alt;

    public DjiNavSatUpdateEvent(double lo, double la, double alt){
        wgs84_long = lo;
        wgs84_lat = la;
        wgs84_alt = alt;
    }
}
