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
        final String strData = bundle.getString(Constants.KEY_CALLER_ID);
        Log.d(TAG, "RECEIVER data: " + strData);
        if (strData != null) {
            Intent broadcastIntent = new Intent("GCMMessage");
            broadcastIntent.putExtra(Constants.KEY_CALLER_ID, strData);
            LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
        }
    }
}
