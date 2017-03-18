package tempmonitor.PatrolManagerHistory;

import java.util.HashMap;

/**
 * Created by Alan on 3/10/2017.
 */

public class LocationsMap {

    public static int getLocationsCount(){
        return getLocationMap().size();
    }

    public static HashMap<String, String> getLocationMap(){
        HashMap<String, String> LOCATION_MAP = new HashMap<>();

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

        return LOCATION_MAP;
    }

    public static String getLocationString(String id){
        return getLocationMap().get(id);
    }

}
