package tempmonitor.PatrolManagerHistory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import tempmonitor.HTTP.getHelper;
import tempmonitor.ReadingsPatrol.PatrolManager;
import com.google.gson.Gson;

import java.util.Arrays;
import tempmonitor.R;

import java.util.List;

/**
 * An activity representing a single PatrolHistory detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link PatrolHistoryListActivity}.
 */
public class PatrolHistoryDetailActivity extends AppCompatActivity {

    public TempLog[] logs;
    private final BroadcastReceiver receiver = new MyReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrolhistory_detail);

        Toolbar toolbar = (Toolbar) PatrolHistoryDetailActivity.this.findViewById(R.id.detail_toolbar);
        toolbar.setTitle("Logs History");
        setSupportActionBar(toolbar);

        String patrol_date = getIntent().getStringExtra("PATROL_TITLE");
        if(patrol_date != null){
            toolbar.setTitle("Logs for : " + patrol_date);
        }

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        IntentFilter filter = new IntentFilter("LOAD_LOGS");
        registerReceiver(receiver, filter);
        getHelper.getPatrolLogs(null, getApplicationContext(), getIntent().getStringExtra(PatrolManager.PM_KEY));

    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter("LOAD_LOGS"));
    }

    @Override
    public void onPause(){
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, PatrolHistoryDetailActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, TempLog[] logs) {
        recyclerView.setAdapter(new PatrolHistoryDetailActivity.SimpleItemRecyclerViewAdapter(logs));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<PatrolHistoryDetailActivity.SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<TempLog> mValues;

        public SimpleItemRecyclerViewAdapter(TempLog[] items) {
            mValues = Arrays.asList(items);
        }

        @Override
        public PatrolHistoryDetailActivity.SimpleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_list_content, parent, false);
            return new PatrolHistoryDetailActivity.SimpleItemRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final PatrolHistoryDetailActivity.SimpleItemRecyclerViewAdapter.ViewHolder holder, final int position) {
            holder.mItem = mValues.get(position);

            holder.mIdView.setText(mValues.get(position).getId());
            holder.mLocation.setText(LocationsMap.getLocationString(mValues.get(position).getLocation_id()));
            holder.mTemp.setText(mValues.get(position).getTemperature());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), mValues.get(position).getInfo(), Toast.LENGTH_SHORT).show();

                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mLocation;
            public final TextView mTemp;
            public TempLog mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.log_tv_id);
                mLocation = (TextView) view.findViewById(R.id.log_tv_location);
                mTemp = (TextView) view.findViewById(R.id.log_tv_temp);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mLocation.getText() + "'";
            }
        }
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // Read data from intent.getExtras()
            // Update UI
            String str_logs = intent.getStringExtra(TempLog.LOG_KEY);
            Gson gson = new Gson();
            logs = gson.fromJson(str_logs, TempLog[].class);

            Log.e("Loaded LOGS", "LOGS: " + str_logs);

            View recyclerView = findViewById(R.id.patrollog_list);
            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView, logs);
        }
    }
}
