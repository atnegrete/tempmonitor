package tempmonitor.Helpers;

import android.text.format.DateUtils;
import android.util.Log;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import tempmonitor.PatrolManagerHistory.PatrolLog;

/**
 * Created by Alan on 3/10/2017.
 */

public class DateHelper {
    /**
     *
     * @param datePicker
     * @return a java.util.Date
     */
    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    /**
     *
     * @return a java.util.Date
     */
    public static String getNowTimeStamp(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        return timeStamp;
    }

    public static Date getDateFromPatrolLog(PatrolLog patrol){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = (Date) df.parse(patrol.getCreated_at());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static ArrayList<ArrayList<PatrolLog>> patrolLogsWeeklyList(PatrolLog[] patrols){
        if(patrols == null || patrols.length == 0) return null;

        ArrayList<ArrayList<PatrolLog>> week = new ArrayList<>();
        Date initial = getDateFromPatrolLog(patrols[0]);
        Date next = addDays(initial, 1);

        for(int i = 0;i < patrols.length; i++){
            week.add(new ArrayList<PatrolLog>());

            int d = i;
            while(d < patrols.length && getDateFromPatrolLog(patrols[i]).getTime() >  initial.getTime()
                                     && getDateFromPatrolLog(patrols[i]).getTime() <= next.getTime()){
                week.get(i).add(patrols[d]);

                initial = addDays(initial, 1);
                next = addDays(next, 1);

                d++;
            }
        }

        return week;
    }

    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }


}
