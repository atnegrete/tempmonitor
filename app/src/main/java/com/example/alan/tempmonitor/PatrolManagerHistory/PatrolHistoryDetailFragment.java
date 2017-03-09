package com.example.alan.tempmonitor.PatrolManagerHistory;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alan.tempmonitor.HTTP.getHelper;
import com.example.alan.tempmonitor.PatrolManager;
import com.example.alan.tempmonitor.R;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

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
    /**
     * The dummy content this fragment is presenting.
     */
    public TempLog[] logs;

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
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                //appBarLayout.setTitle(logs[0].getLocation());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.patrolhistory_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (logs != null) {
            ((TextView) rootView.findViewById(R.id.log_location)).setText(logs.toString());
        }

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

        }
    }

//    private void setupRecyclerView(@NonNull RecyclerView recyclerView, PatrolManagerHistoryItem[] history) {
//        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(history));
//    }
//
//    public class SimpleItemRecyclerViewAdapter
//            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {
//
//        private final List<PatrolManagerHistoryItem> mValues;
//
//        public SimpleItemRecyclerViewAdapter(PatrolManagerHistoryItem[] items) {
//            mValues = Arrays.asList(items);
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patrolhistory_list_content, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, final int position) {
//            holder.mItem = mValues.get(position);
//            holder.mIdView.setText(mValues.get(position).getStringId());
//            holder.mDateView.setText(mValues.get(position).getDatetime());
//            holder.mUserView.setText(mValues.get(position).getUser());
//
//            holder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(), "ID: " + mValues.get(position).getStringId(), Toast.LENGTH_LONG).show();
//                    if (mTwoPane) {
//                        Bundle arguments = new Bundle();
//                        arguments.putString(PatrolHistoryDetailFragment.ARG_ITEM_ID, holder.mItem.getStringId());
//                        PatrolHistoryDetailFragment fragment = new PatrolHistoryDetailFragment();
//                        fragment.setArguments(arguments);
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.patrolhistory_detail_container, fragment)
//                                .commit();
//                    } else {
//                        //getHelper.getPatrolLogs(null, getApplicationContext(), mValues.get(position).getStringId());
//                        Intent intent = new Intent(PatrolHistoryListActivity.this, PatrolHistoryDetailActivity.class);
//                        intent.putExtra(PatrolManager.PM_KEY, holder.mItem.getStringId());
//                        startActivity(intent);
//                    }
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return mValues.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            public final View mView;
//            public final TextView mIdView;
//            public final TextView mDateView;
//            public final TextView mUserView;
//            public PatrolManagerHistoryItem mItem;
//
//            public ViewHolder(View view) {
//                super(view);
//                mView = view;
//                mIdView = (TextView) view.findViewById(R.id.pmh_id);
//                mDateView = (TextView) view.findViewById(R.id.pmh_date);
//                mUserView = (TextView) view.findViewById(R.id.pmg_user);
//            }
//
//            @Override
//            public String toString() {
//                return super.toString() + " '" + mDateView.getText() + "'";
//            }
//        }
//    }
}
