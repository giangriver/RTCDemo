package enc.harvey.webrtc.rtcdemo.gcm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import enc.harvey.webrtc.rtcdemo.utils.Constants;

/**
 * Created by harold on 10/05/2016.
 */
public class MessageReceiver extends WakefulBroadcastReceiver {
    private final String TAG = MessageReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "==============================================================================");
        Log.d(TAG, "RECEIVER data: " + bundle.getString("data"));
        Log.d(TAG, "RECEIVER message: " + bundle.getString("message"));
        Log.d(TAG, "RECEIVER to: " + bundle.getString("to"));
        Log.d(TAG, "RECEIVER type: " + bundle.getString("type"));
        Log.d(TAG, "RECEIVER payload: " + bundle.getString("payload"));
        Log.d(TAG, "==============================================================================");
        final String callerId = bundle.getString(Constants.KEY_CALLER_ID);
        if (callerId != null) {
            Intent broadcastIntent = new Intent("GCMMessage");
            broadcastIntent.putExtra(Constants.KEY_CALLER_ID, callerId);
            LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
        }
    }
}
