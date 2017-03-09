package com.example.alan.tempmonitor.PatrolManagerHistory;

import java.util.HashMap;

/**
 * Created by anegrete on 3/5/2017.
 */
public class TempLog {

    public static final HashMap<String, String> LOCATION_MAP = new HashMap<>();
    public static final String LOG_KEY = "log_key";

    public String id;
    public String temp;
    public String location;

    public TempLog(String id, String temp, String location){
        this.id = id;
        this.temp = temp;
        this.location = location;

        loadLocationsMap();
    }

    public void loadLocationsMap(){
        LOCATION_MAP.put("1", "Admin 1st Floor");
        LOCATION_MAP.put("2", "Admin 2nd Floor");
        LOCATION_MAP.put("3", "Admin 3rd Floor");
        LOCATION_MAP.put("4", "N.W. 1st Floor");
        LOCATION_MAP.put("5", "N.W. 2st Floor");
        LOCATION_MAP.put("6", "N.W. 3st Floor");
        LOCATION_MAP.put("7", "N.E. 1st Floor");
        LOCATION_MAP.put("8", "N.E. 2st Floor");
        LOCATION_MAP.put("9", "N.E. 3st Floor");
        LOCATION_MAP.put("10", "S. Wing 1st Floor");
        LOCATION_MAP.put("11", "S. Wing 2st Floor");
        LOCATION_MAP.put("12", "W. Wing 3st Floor");
        LOCATION_MAP.put("13", "N. Annex Basement");
        LOCATION_MAP.put("14", "N. Annex 1st Floor");
        LOCATION_MAP.put("15", "District 1 Basement");
        LOCATION_MAP.put("16", "District 1 1st Floor");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getLocation() {
        return LOCATION_MAP.get(this.location);
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
