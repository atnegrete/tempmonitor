package tempmonitor.HTTP;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import tempmonitor.PatrolManagerHistory.PatrolLog;
import tempmonitor.PatrolManagerHistory.TempLog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * Created by anegrete on 3/5/2017.
 */
public class getHelper {

    public static void getPatrolLogs(RequestParams params, final Context context, String patrol_id) {

        // Patrol Id >> -1 to get Log::all()
        TempMonitorRestClient.post("/history-logs/"+patrol_id, params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Intent intent = new Intent("LOAD_LOGS");
                intent.putExtra(TempLog.LOG_KEY, responseString);

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

    public static void getPatrolHistory(RequestParams params, final Context context, String date) {

        TempMonitorRestClient.get("/date-history/"+date, params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {


                Intent intent = new Intent("LOAD_HISTORY");
                intent.putExtra("history_key", responseString);

                AsyncHttpClient.log.e(AsyncHttpClient.LOG_TAG, "SUCCESS_MESSAGE : " + responseString.toString());

                context.sendBroadcast(intent);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("FAILED", "Code: "+ statusCode + "  Response: " +  throwable.toString());
                Toast.makeText(context, "Error Code: " + statusCode + ". Check your internet settings or try again later.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRetry(int retryNo) {
                Log.e("FAILED", "Retry No: "+ retryNo);
            }

        });
    }

    public static void getPatrolsWithLogs(RequestParams params, final Context context, String patrol_id) {

        // Patrol Id >> -1 to get Log::all()
        TempMonitorRestClient.get("/patrol-and-logs/"+patrol_id, params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Intent intent = new Intent("LOAD_PATROLS_LOGS");
                intent.putExtra(PatrolLog.PATROL_LOGS_KEY, responseString);

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

}
