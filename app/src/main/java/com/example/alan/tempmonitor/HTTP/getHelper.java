package com.example.alan.tempmonitor.HTTP;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.alan.tempmonitor.PatrolManagerHistory.PatrolHistoryDetailActivity;
import com.example.alan.tempmonitor.PatrolManagerHistory.PatrolHistoryDetailFragment;
import com.example.alan.tempmonitor.PatrolManagerHistory.PatrolHistoryListActivity;
import com.example.alan.tempmonitor.PatrolManagerHistory.TempLog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * Created by anegrete on 3/5/2017.
 */
public class getHelper {

    public static void getPatrolLogs(RequestParams params, final Context context, String id) {

        TempMonitorRestClient.post("/history-logs/"+id, params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {

//                Intent intent = new Intent(context, PatrolHistoryDetailActivity.class);
//                intent.putExtra(TempLog.LOG_KEY, responseString);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);

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
}
