package tempmonitor.Helpers;

import android.widget.DatePicker;

import java.util.Calendar;

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
}
