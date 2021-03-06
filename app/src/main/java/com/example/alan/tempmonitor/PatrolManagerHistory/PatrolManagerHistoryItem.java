package com.example.alan.tempmonitor.PatrolManagerHistory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PatrolManagerHistoryItem {

    public int id;
    public String user;
    public String created_at;

    public PatrolManagerHistoryItem(int id, String user, String created_at, String updated_at){
        this.id = id;
        this.user = user;
        this.created_at = created_at;
    }

    public String getDatetime() {
        return created_at;
    }

    public void setDatetime(String datetime) {
        this.created_at = datetime;
    }

    public int getId() {
        return id;
    }

    public String getStringId() { return new Integer(getId()).toString(); }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
