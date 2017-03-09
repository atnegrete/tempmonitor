package com.example.alan.tempmonitor.FileHandlers;

import android.content.Context;

import com.example.alan.tempmonitor.PatrolManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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


}
