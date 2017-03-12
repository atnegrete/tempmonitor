package tempmonitor.FileHandlers;

import android.content.Context;

import tempmonitor.ReadingsPatrol.PatrolManager;
import tempmonitor.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Alan on 3/1/2017.
 */

public class FileManager {

    public static final String PM_FILE = "PM_FILE";
    public static final String SE_USER = "SE_USER";


    public static boolean savePatrolManager(Context context, PatrolManager pm){
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(PM_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(pm);
            os.close();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static PatrolManager loadPatrolManager(Context context) {
        FileInputStream fis = null;
        ObjectInputStream is = null;
        PatrolManager patrolManager = null;
        try {
            fis = context.openFileInput(PM_FILE);
            is = new ObjectInputStream(fis);
            patrolManager = (PatrolManager) is.readObject();

            is.close();
            fis.close();
        } catch (InvalidClassException e){
            e.printStackTrace();
        } catch (ClassNotFoundException | IOException e) {
            //e.printStackTrace();
        }
        return patrolManager;
    }

    public static boolean saveUserName(Context context, User user){
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(SE_USER, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(user);
            os.close();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static User loadUser(Context context) {
        FileInputStream fis = null;
        ObjectInputStream is = null;
        User user = null;
        try {
            fis = context.openFileInput(SE_USER);
            is = new ObjectInputStream(fis);
            user = (User) is.readObject();

            is.close();
            fis.close();
        } catch (InvalidClassException e){
            e.printStackTrace();
        } catch (ClassNotFoundException | IOException e) {
            //e.printStackTrace();
        }
        return user;
    }


}
