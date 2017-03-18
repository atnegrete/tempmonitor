package tempmonitor.PatrolManagerHistory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alan on 3/14/2017.
 */

public class PatrolLog {

    public static final String PATROL_LOGS_KEY = "patrol_logs_key";

    public String id;
    public String user;
    public String created_at;
    public String updated_at;
    public TempLog[] logs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public TempLog[] getLogs() {
        return logs;
    }

    public void setLogs(TempLog[] logs) {
        this.logs = logs;
    }

    public Date getDateObject(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(getCreated_at());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Integer getMilitaryTime(){
        Date date = getDateObject();
        int hours = date.getHours();
        int minutes = date.getMinutes();
        String time = ""+ String.valueOf(hours) + String.valueOf(minutes);
        return new Integer(time);
    }
}
