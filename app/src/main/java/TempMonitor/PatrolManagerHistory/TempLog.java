package tempmonitor.PatrolManagerHistory;

/**
 * Created by anegrete on 3/5/2017.
 */
public class TempLog {

    public static final String LOG_KEY = "log_key";

    public String id;
    public String temperature;
    public String location_id;
    public String patrol_id;

    public TempLog(String id, String temperature, String location_id, String patrol_id){
        this.id = id;
        this.temperature = temperature;
        this.location_id = location_id;
        this.patrol_id = patrol_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemperature() {
        return (temperature == "0") ? "N/A" : temperature + "\u00b0" + "F";
    }

    public void setTemperature(String temp) {
        this.temperature = temp;
    }

    public void setLocation_id(String location) {
        this.location_id = location;
    }

    public String getLocation_id(){
        return this.location_id;
    }

    public String getInfo(){
        return "Info : " + LocationsMap.getLocationString(getLocation_id()) + " - " + getTemperature();
    }

}
