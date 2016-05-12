package enc.harvey.webrtc.rtcdemo.gcm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by harold on 10/05/2016.
 */
public class MessageReceiver extends WakefulBroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        final String strData = bundle.getString("caller_id");
        if (strData != null) {
            Intent broadcastIntent = new Intent("GCMMessage");
            broadcastIntent.putExtra("caller_id", strData);
            Log.i("RECEIVER", "AAAAAAAAAAAAAAAAAAAAAAAAAAaa");
            LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);

        }

    }
}
