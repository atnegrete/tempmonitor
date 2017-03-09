package com.example.alan.tempmonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alan.tempmonitor.FileHandlers.FileManager;
import com.example.alan.tempmonitor.dummy.TempItemMaker;

import java.io.File;

import static android.view.inputmethod.EditorInfo.IME_ACTION_NEXT;
import static java.lang.StrictMath.max;

/**
 * A fragment representing a single Reading detail screen.
 * This fragment is either contained in a {@link ReadingListActivity}
 * in two-pane mode (on tablets) or a {@link ReadingDetailActivity}
 * on handsets.
 */
public class ReadingDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private TempItemMaker.TemperatureItem mItem;

    public PatrolManager patrol_manager;
    public Integer index;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReadingDetailFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        patrol_manager = FileManager.loadPatrolManager(getActivity().getApplicationContext());

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            index = new Integer(getArguments().get(ARG_ITEM_ID).toString()) - 1;
            mItem = patrol_manager.getItems().get(index);
            Log.e("LOADED ITEM", "TEMP " + (index+1) + " - temp : " + new Double(mItem.temp.toString()));

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final AppCompatActivity current = ((AppCompatActivity) getActivity());
        current.getSupportActionBar().setTitle(mItem.content);

        View rootView = inflater.inflate(R.layout.reading_temp, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.reading_detail_text)).setText(mItem.details);

            final EditText temp = ((EditText) rootView.findViewById(R.id.single_temp_input));
            temp.setText(mItem.temp.toString());

            final Button submitTemp = (Button) rootView.findViewById(R.id.submit_single_temp);

            if(submitTemp != null){
                submitTemp.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        submitTemp.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_pressed_states));

                    // Check input
                    if(validateInput(temp.getText().toString())){
                        patrol_manager.setTemp(index, new Double(temp.getText().toString()) );

                        FileManager.savePatrolManager(getActivity().getApplicationContext(), patrol_manager);
                        Log.e("UPDATE", "Patrol Manager Saved - Temp updated on " + (index+1) + " - temp : " + new Double(mItem.temp.toString()));

                        Intent returnIntent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(PatrolManager.PM_KEY, patrol_manager);
                        returnIntent.putExtras(bundle);
                        getActivity().setResult(Activity.RESULT_CANCELED, returnIntent);
                        getActivity().finish();
                    }
                    }
                });
            }

        }

        return rootView;
    }

    private boolean validateInput(String num) {

        Toast.makeText(getContext(), num, Toast.LENGTH_SHORT).show();

        Double temp = Double.parseDouble(num);

        // Check for possible Error
        if(temp > 120 || temp < 0){
            Toast.makeText(getContext(), "Check for correct temperature", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
