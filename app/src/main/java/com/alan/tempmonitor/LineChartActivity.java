package com.alan.tempmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;

import tempmonitor.HTTP.getHelper;
import tempmonitor.Models.LineDataSetBuilder;
import tempmonitor.PatrolManagerHistory.LocationsMap;
import tempmonitor.PatrolManagerHistory.PatrolLog;
import tempmonitor.PatrolManagerHistory.TempLog;
import tempmonitor.R;

public class LineChartActivity extends AppCompatActivity {

    private final BroadcastReceiver receiver = new MyReceiver();
    LineChart line_chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        line_chart = (LineChart) findViewById(R.id.line_chart);

        IntentFilter filter = new IntentFilter("LOAD_PATROLS_LOGS");
        registerReceiver(receiver, filter);
        getHelper.getPatrolsWithLogs(null, getApplicationContext(), "-1");

    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String str_logs = intent.getStringExtra(PatrolLog.PATROL_LOGS_KEY);
            Gson gson = new Gson();
            PatrolLog[] logs = gson.fromJson(str_logs, PatrolLog[].class);

            Log.e("Loaded LOGS", "LOGS: " + str_logs);

            loadLineGraph(logs);

        }
    }

    private void loadLineGraph(PatrolLog[] patrols) {
        int num_logs = patrols.length;


        LineData lineData = new LineData();

        ArrayList<LineDataSet> dataSets = LineDataSetBuilder.getDataSets(patrols);

        for (LineDataSet lds: dataSets) {
            lineData.addDataSet(lds);
        }


        Legend legend = line_chart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        legend.setEnabled(true);
        legend.setWordWrapEnabled(true);
        legend.setMaxSizePercent(.95f);
        line_chart.getAxisLeft().setAxisMinimum(65f);
        line_chart.getAxisRight().setAxisMinimum(65f);
        line_chart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String time = String.valueOf((int) value);
                time = (time.length() == 3) ? "0" + time : time;
                return time;
            }
        });
        line_chart.setData(lineData);
        line_chart.invalidate();

        LineDataSetBuilder.mergePatrolsToWeek(patrols);
    }

    public LineDataSet getLineDataSed(PatrolLog[]  patrols, int location_index, int color){
        ArrayList<Entry> entries = new ArrayList<>();

        for (int p = 0; p < patrols.length; p++) {
            PatrolLog patrol = patrols[p];

            Float temp = patrol.getLogs()[location_index-1].getTemperatureFloat();

            entries.add(new Entry(p, temp));
        }

        LineDataSet dataSet = new LineDataSet(entries, LocationsMap.getLocationString(String.valueOf(location_index)));
        dataSet.setDrawCircles(false);
        dataSet.setColor(color);
        return dataSet;
    }
}
