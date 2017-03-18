package tempmonitor.PatrolManagerHistory;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import tempmonitor.R;

import tempmonitor.HTTP.getHelper;
import tempmonitor.ReadingsPatrol.PatrolManager;
import com.google.gson.Gson;

/**
 * A fragment representing a single PatrolHistory detail screen.
 * This fragment is either contained in a {@link PatrolHistoryListActivity}
 * in two-pane mode (on tablets) or a {@link PatrolHistoryDetailActivity}
 * on handsets.
 */
public class PatrolHistoryDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public final BroadcastReceiver receiver = new myBroadCastReceiver();
    public TempLog[] logs;
    public ListView list_view;
    View rootView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PatrolHistoryDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(PatrolManager.PM_KEY)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            IntentFilter filter = new IntentFilter("LOAD_LOGS");
            getActivity().getApplicationContext().registerReceiver(receiver, filter);
            getHelper.getPatrolLogs(null, getActivity().getApplicationContext(), getArguments().getString(PatrolManager.PM_KEY));

            Activity activity = this.getActivity();
            Toolbar toolbar = (Toolbar) activity.findViewById(R.id.detail_toolbar);
            if(toolbar != null){
                toolbar.setTitle(getArguments().getString("PATROL_TITLE"));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.log_list_content, container, false);

        return rootView;
    }

    private class myBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Read data from intent.getExtras()
            // UPdate UI

            String str_logs = intent.getStringExtra(TempLog.LOG_KEY);
            Gson gson = new Gson();
            logs = gson.fromJson(str_logs, TempLog[].class);
//
//            if (logs != null && rootView != null) {
//                list_view = (ListView) getActivity().findViewById(R.id.lv_logs);
//                adapter = new TempLogListAdapter(getContext(), logs);
//                list_view.setAdapter(adapter);
//            }

        }
    }


}
