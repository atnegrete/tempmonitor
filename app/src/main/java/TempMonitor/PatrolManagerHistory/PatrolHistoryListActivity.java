package tempmonitor.PatrolManagerHistory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.Toast;

import tempmonitor.HTTP.getHelper;
import tempmonitor.ReadingsPatrol.PatrolManager;
import tempmonitor.R;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

/**
 * An activity representing a list of Logs. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PatrolHistoryDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class PatrolHistoryListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    public PatrolManagerHistoryItem[] history;
    private final BroadcastReceiver receiver = new MyReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrolhistory_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Patrols History");
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        IntentFilter filter = new IntentFilter("LOAD_HISTORY");
        registerReceiver(receiver, filter);
        getHelper.getPatrolHistory(null, getApplicationContext(), getIntent().getStringExtra(DatePatrolHistory.DPH_DATE_KEY));

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter("LOAD_HISTORY"));
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
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, PatrolManagerHistoryItem[] history) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(history));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<PatrolManagerHistoryItem> mValues;

        public SimpleItemRecyclerViewAdapter(PatrolManagerHistoryItem[] items) {
            mValues = Arrays.asList(items);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patrolhistory_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).getStringId());
            holder.mDateView.setText(mValues.get(position).getDatetime());
            holder.mUserView.setText(mValues.get(position).getUser());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Selected: " + mValues.get(position).getDatetime(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(PatrolHistoryListActivity.this, PatrolHistoryDetailActivity.class);
                intent.putExtra(PatrolManager.PM_KEY, holder.mItem.getStringId());
                intent.putExtra("PATROL_TITLE", mValues.get(position).getDatetime());
                startActivity(intent);
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
            public final TextView mDateView;
            public final TextView mUserView;
            public PatrolManagerHistoryItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.pmh_id);
                mDateView = (TextView) view.findViewById(R.id.pmh_date);
                mUserView = (TextView) view.findViewById(R.id.pmh_user);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mDateView.getText() + "'";
            }
        }
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // Read data from intent.getExtras()
            // Update UI
            String string_h = intent.getStringExtra("history_key");
            Gson gson = new Gson();
            history = gson.fromJson(string_h, PatrolManagerHistoryItem[].class);

            View recyclerView = findViewById(R.id.patrolhistory_list);
            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView, history);
        }
    }
}
