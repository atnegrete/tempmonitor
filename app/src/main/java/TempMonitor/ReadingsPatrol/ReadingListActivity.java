package tempmonitor.ReadingsPatrol;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import tempmonitor.FileHandlers.FileManager;
import tempmonitor.ReadingsPatrol.dummy.TempItemMaker;
import tempmonitor.User;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import tempmonitor.R;

import java.util.List;

/**
 * An activity representing a list of Readings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ReadingDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ReadingListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public View recyclerView;
    public SimpleItemRecyclerViewAdapter adapter;
    public Toolbar toolbar;
    public ProgressBar progress_bar;
    public PatrolManager patrol_manager;
    public Button finish;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_list);

//        /*
//         * First check that it doesn't already exist.
//         */
//        patrol_manager = FileManager.loadPatrolManager(getApplicationContext());
//
//        if (patrol_manager == null) {
//            patrol_manager = new PatrolManager(TempItemMaker.ITEMS);
//        }

        patrol_manager = (PatrolManager) getIntent().getSerializableExtra(PatrolManager.PM_KEY);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        /*
        Progress bar set up.
         */
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        if (progress_bar != null) {
            progress_bar.setMax(patrol_manager.getItems().size());
            progress_bar.setProgress(patrol_manager.getProgress());
        }

        /*
        Finish  patrol button setup.
         */
        finish = (Button) findViewById(R.id.button_finish_patrol);
        if (finish != null) {
            showMyAlertDialog();
        }

        recyclerView = findViewById(R.id.reading_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.reading_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();

        patrol_manager = FileManager.loadPatrolManager(getApplicationContext());

        progress_bar.setProgress(patrol_manager.getProgress());

        recyclerView = findViewById(R.id.reading_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {


        adapter = new SimpleItemRecyclerViewAdapter(patrol_manager.getItems());
        recyclerView.setAdapter(adapter);
    }

    public void showMyAlertDialog() {

        finish.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                /*
                If Patrol is complete, do logic to finish it.
                 */
                if (patrol_manager.getProgress() == patrol_manager.getItems().size()) {
                    getUsernameDialog();
                    Toast.makeText(ReadingListActivity.this, "Finished patrol.", Toast.LENGTH_SHORT).show();
                }
                // Patrol is not complete, make sure user wants to save it.
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ReadingListActivity.this);
                    alertDialogBuilder.setMessage("Readings have not been completed. Are you sure you want to finish?");

                    //Clicked yes.
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(ReadingListActivity.this, "Finished patrol.", Toast.LENGTH_SHORT).show();
                            getUsernameDialog();
                        }
                    });

                    //Clicked no
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(ReadingListActivity.this, "Continue patrol.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getUsernameDialog() {

        User user = (User) FileManager.loadUser(getApplicationContext());
        if(user == null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(ReadingListActivity.this);
            builder.setTitle("Enter your name.");

            // Set up the input
            final EditText input = new EditText(ReadingListActivity.this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint("Set name in settings..");
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String m_Text = input.getText().toString();
                    patrol_manager.setUser(m_Text);
                    //Toast.makeText(ReadingListActivity.this, "name: " + m_Text, Toast.LENGTH_SHORT).show();
                }
            });

            builder.show();
        }else{
            patrol_manager.setUser(user.getUsername());

        }

        patrol_manager.finishPatrol(ReadingListActivity.this);
    }



    /**
     * The Simple Reycler View Adapter Class to load the List View.
     */
    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private List<TempItemMaker.TemperatureItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<TempItemMaker.TemperatureItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reading_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            if (mValues.get(position).checked == 1) {
                holder.rl.setBackgroundColor(getResources().getColor(R.color.myLightGreen));
                holder.mTemp.setText(mValues.get(position).temp.toString() + "\u00b0" + "F");
                holder.mTemp.setVisibility(TextView.VISIBLE);
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*
                    Save the PatrolManager
                     */
                    FileManager.savePatrolManager(getApplicationContext(), patrol_manager);
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ReadingDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        ReadingDetailFragment fragment = new ReadingDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.reading_detail_container, fragment)
                                .commit();

                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ReadingDetailActivity.class);
                        intent.putExtra(ReadingDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        context.startActivity(intent);
                        Log.e("pane check", "else block");
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            RelativeLayout rl;
            View mView;
            TextView mIdView;
            TextView mContentView;
            TextView mTemp;
            TempItemMaker.TemperatureItem mItem;

            ViewHolder(View view) {
                super(view);

                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
                mTemp = (TextView) view.findViewById(R.id.tv_temp);
                rl = (RelativeLayout) view.findViewById(R.id.row);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
