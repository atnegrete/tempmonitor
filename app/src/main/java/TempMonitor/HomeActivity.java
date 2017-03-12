package tempmonitor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import tempmonitor.FileHandlers.FileManager;
import tempmonitor.PatrolManagerHistory.DatePatrolHistory;
import tempmonitor.ReadingsPatrol.PatrolManager;
import tempmonitor.ReadingsPatrol.ReadingListActivity;
import tempmonitor.ReadingsPatrol.dummy.TempItemMaker;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction ft;
    PatrolManager patrol_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frame_container, new HomePageContent());
        ft.commit();
    }

    private void alertDialogToContinuePatrol() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
        alertDialogBuilder.setMessage("There a patrol in progress. Would you like to continue it? A new one will erase all previous progress.");

        //Clicked new.
        alertDialogBuilder.setPositiveButton("New Patrol", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(HomeActivity.this, "New Patrol", Toast.LENGTH_SHORT).show();

                patrol_manager = new PatrolManager(TempItemMaker.ITEMS);

                FileManager.savePatrolManager(getApplicationContext(), patrol_manager);

                loadStartLoggingFragment(patrol_manager);
            }
        });

        //Clicked continue
        alertDialogBuilder.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(HomeActivity.this, "Continue Patrol", Toast.LENGTH_SHORT).show();

                patrol_manager = FileManager.loadPatrolManager(getApplicationContext());

                if(patrol_manager == null){
                    Toast.makeText(HomeActivity.this, "Error loading file. New one has been created.", Toast.LENGTH_SHORT).show();
                }
                loadStartLoggingFragment(patrol_manager);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.start_logging) {
            // Check if there a current patrol.
            patrol_manager = FileManager.loadPatrolManager(getApplicationContext());
            if(patrol_manager == null || patrol_manager.getProgress() == 0){
                patrol_manager = new PatrolManager(TempItemMaker.ITEMS);
                FileManager.savePatrolManager(getApplicationContext(), patrol_manager);

                loadStartLoggingFragment(patrol_manager);
            }else{
                alertDialogToContinuePatrol();
            }

        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(HomeActivity.this, DatePatrolHistory.class);
            startActivity(intent);
//            Intent intent = new Intent(HomeActivity.this, PatrolHistoryListActivity.class);
//            startActivity(intent);

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadStartLoggingFragment(PatrolManager pm) {

        Bundle bundle = new Bundle();
        Intent intent = new Intent(getApplicationContext(),ReadingListActivity.class);
        bundle.putSerializable(PatrolManager.PM_KEY, pm);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
