package tempmonitor;

import java.io.Serializable;

/**
 * Created by Alan on 3/10/2017.
 */

public class User implements Serializable{

    String username;

    public User(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
