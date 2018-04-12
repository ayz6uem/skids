package com.ybyc.simulator.car.common.location;

import java.math.BigDecimal;

/**
 * 坐标转换
 * @author wangzhe
 */
public class Location {

    public static final double PI = 3.1415926535897932384626;
    public static final double a = 6378245.0;
    public static final double ee = 0.00669342162296594323;

    Point point;

    public Location(double lng, double lat) {
        this.point = new Point(lng,lat);
    }

    /**
     * WGS84转GCj02
     * @returns {*[]}
     */
    public Point toGcj02(){
        double lng = point.lng;
        double lat = point.lat;
        double dlat = transformlat(lng - 105.0, lat - 35.0);
        double dlng = transformlng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
        double mglat = lat + dlat;
        double mglng = lng + dlng;
        point=new Point(new BigDecimal(mglng).setScale(6,BigDecimal.ROUND_HALF_UP).doubleValue(),
                new BigDecimal(mglat).setScale(6,BigDecimal.ROUND_HALF_UP).doubleValue());
        return point;
    }

    /**
     * GCJ02 转换为 WGS84
     * @returns {*[]}
     */
    public Point toWgs84(){
        double lng = point.lng;
        double lat = point.lat;
        double dlat = transformlat(lng - 105.0, lat - 35.0);
        double dlng = transformlng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
        double mglat = lat + dlat;
        double mglng = lng + dlng;
        point=new Point(new BigDecimal(lng * 2 - mglng).setScale(6,BigDecimal.ROUND_HALF_UP).doubleValue(),
                new BigDecimal(lat * 2 - mglat).setScale(6,BigDecimal.ROUND_HALF_UP).doubleValue());
        return point;
    }


    private double transformlat(double lng,double lat){
        double ret= -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private double transformlng(double lng,double lat){
        double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    public class Point{
        public double lng;
        public double lat;

        public Point(double lng, double lat) {
            this.lng = lng;
            this.lat = lat;
        }
    }
}