package tempmonitor.Models;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.util.ArrayList;
import java.util.Date;

import tempmonitor.Helpers.DateHelper;
import tempmonitor.PatrolManagerHistory.LocationsMap;
import tempmonitor.PatrolManagerHistory.PatrolLog;
import tempmonitor.PatrolManagerHistory.TempLog;

/**
 * Created by Alan on 3/14/2017.
 */

public class LineDataSetBuilder {

    public static ArrayList<LineDataSet> getDataSets(PatrolLog[] patrols) {
        ArrayList<LineDataSet> list = new ArrayList<>();

        list.add(getLineDataSet(patrols, 1, Color.RED));
        list.add(getLineDataSet(patrols, 2, Color.BLUE));
        list.add(getLineDataSet(patrols, 3, Color.GREEN));
        list.add(getLineDataSet(patrols, 4, Color.CYAN));
        list.add(getLineDataSet(patrols, 5, Color.YELLOW));
        list.add(getLineDataSet(patrols, 6, Color.MAGENTA));
        list.add(getLineDataSet(patrols, 7, Color.GRAY));
        list.add(getLineDataSet(patrols, 8, Color.BLACK));
        list.add(getLineDataSet(patrols, 9, Color.rgb(10,10,30)));
        list.add(getLineDataSet(patrols, 10, Color.rgb(30,10,20)));
        list.add(getLineDataSet(patrols, 11, Color.rgb(40,10,10)));
        list.add(getLineDataSet(patrols, 12, Color.rgb(50,10,0)));

        return list;
    }

    public static PatrolLog[] mergePatrolsToWeek(PatrolLog[] patrols) {

        DateHelper.patrolLogsWeeklyList(patrols);

        PatrolLog[] merged = new PatrolLog[7];

        for(int i = 0; i < patrols.length; i++){
            Date date = DateHelper.getDateFromPatrolLog(patrols[i]);

        }

        return merged;
    }

    public static LineDataSet getLineDataSet(PatrolLog[]  patrols, int location_index, int color){
        ArrayList<Entry> entries = new ArrayList<>();

        for (int p = 0; p < patrols.length; p++) {
            PatrolLog patrol = patrols[p];

            Float temp = patrol.getLogs()[location_index-1].getTemperatureFloat();

            entries.add(new Entry(patrols[p].getMilitaryTime(), temp));
        }

        LineDataSet dataSet = new LineDataSet(entries, LocationsMap.getLocationString(String.valueOf(location_index)));
        dataSet.setDrawCircles(false);
        dataSet.setColor(color);
        return dataSet;
    }

    public static PatrolLog averageDailyPatrolsToOne(PatrolLog[] patrols){

        if(patrols == null || patrols.length == 0)
            return null;

        PatrolLog avg_patrol = new PatrolLog();
        Float[] avg_logs = new Float[LocationsMap.getLocationsCount()];
        TempLog[] logs = new TempLog[LocationsMap.getLocationsCount()];


        for(int i = 0; i < patrols.length; i++){

            for(int j = 0;j < patrols[i].getLogs().length; j++){
                avg_logs[j] += patrols[i].getLogs()[j].getTemperatureFloat();
            }

        }

        for(int i = 0; i < avg_logs.length; i++){
            avg_logs[i] = (avg_logs[i] / avg_logs.length+1);
            logs[i].setTemperature(String.valueOf(avg_logs[i]));
        }

        avg_patrol.setLogs(logs);
        avg_patrol.setCreated_at(patrols[0].getCreated_at());
        
        return avg_patrol;
    }
}
