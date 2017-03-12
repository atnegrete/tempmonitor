package tempmonitor.ReadingsPatrol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import tempmonitor.FileHandlers.FileManager;
import tempmonitor.HTTP.TempMonitorRestClient;
import tempmonitor.ReadingsPatrol.dummy.TempItemMaker;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alan on 2/27/2017.
 */

public class PatrolManager implements Serializable {

    public static final String PM_KEY = "patrol_manager";

    private List<TempItemMaker.TemperatureItem> temp_items;

    public String getUser() {
        return user;
    }

    private String user;

    public String id;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    private String datetime;
    private int progress;

    public PatrolManager(List<TempItemMaker.TemperatureItem> items) {
        progress = 0;
        this.temp_items = items;
        this.user = "Unknown";
    }

    public List<TempItemMaker.TemperatureItem> getItems() {
        return temp_items;
    }

    public void setTemp(int index, Double temp){
        TempItemMaker.TemperatureItem item = temp_items.get(index);
        if(item.checked == 0){
            progress++;
            item.checked = 1;
        }
        temp_items.get(index).temp = temp;
    }

    public int getProgress() {
        return progress;
    }

    public void setUser(String user){
        this.user = user;
    }

    public static void getPatrolHistory(RequestParams params, final Context context) {

        TempMonitorRestClient.post("/history", params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {

//                Bundle bundle = new Bundle();
//                Intent intent = new Intent(context, PatrolHistoryListActivity.class);
//                bundle.putString("history_key", responseString);
//                intent.putExtras(bundle);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                AsyncHttpClient.log.e(AsyncHttpClient.LOG_TAG, "SUCCESS_MESSAGE : " + responseString.toString());
//
//                context.startActivity(intent);

                Intent intent = new Intent("LOAD_HISTORY");
                intent.putExtra("history_key", responseString);

                AsyncHttpClient.log.e(AsyncHttpClient.LOG_TAG, "SUCCESS_MESSAGE : " + responseString.toString());

                context.sendBroadcast(intent);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("FAILED", "Code: "+ statusCode + "  Response: " +  throwable.toString());
            }

            @Override
            public void onRetry(int retryNo) {
                Log.e("FAILED", "Retry No: "+ retryNo);
            }

        });
    }

    public static void postCompletePatrol(final Context context, RequestParams params){
        TempMonitorRestClient.post("/complete-patrol", params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Gson gson = new Gson();
                PatrolManager patrol_manager = gson.fromJson(responseString, PatrolManager.class);

                AsyncHttpClient.log.e(AsyncHttpClient.LOG_TAG, "SUCCESS_MESSAGE : " + responseString);

                Toast.makeText(context, "Sync. Successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("FAILED", "Code: "+ statusCode + "  Response: " +  throwable.toString());
                Toast.makeText(context, "Sync. error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                Log.e("FAILED", "Retry No: "+ retryNo);
            }

        });
    }

    /**
     * Send data to DB.
     *
     * @param activity
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void finishPatrol(Activity activity) {
        RequestParams params = new RequestParams();
        params.put(PatrolManager.PM_KEY, this.toJson());

        PatrolManager.postCompletePatrol(activity.getApplicationContext(), params);

        FileManager.savePatrolManager(activity.getApplicationContext(), new PatrolManager(TempItemMaker.ITEMS));
        activity.finish();
    }

    public String toJson(){
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }


}
