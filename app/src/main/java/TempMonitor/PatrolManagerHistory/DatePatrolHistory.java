package tempmonitor.PatrolManagerHistory;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import tempmonitor.Helpers.DateHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import tempmonitor.R;


public class DatePatrolHistory extends AppCompatActivity {

    public static final String DPH_DATE_KEY = "DPH_DATE_KEY";

    DatePicker date_picker;
    Button btn_find_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_patrol_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("View Logs by Date");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){ actionBar.setDisplayHomeAsUpEnabled(true); }

        date_picker = (DatePicker) findViewById(R.id.dp_date_picker);
        btn_find_history = (Button) findViewById(R.id.btn_find_history);

        btn_find_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = DateHelper.getDateFromDatePicker(date_picker);

                Toast.makeText(getApplicationContext(), dateFormat.format(date), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DatePatrolHistory.this, PatrolHistoryListActivity.class);
                intent.putExtra(DPH_DATE_KEY, dateFormat.format(date));
                startActivity(intent);
            }
        });


    }
}
