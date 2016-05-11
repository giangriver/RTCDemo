package enc.harvey.webrtc.rtcdemo.gcm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import enc.harvey.webrtc.rtcdemo.activity.CallActivity;

/**
 * Created by harold on 10/05/2016.
 */
public class MessageReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Intent broadcastIntent = new Intent("GCMMessage");
        broadcastIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        String message = bundle.getString("message");
        String name = bundle.getString("title");
        broadcastIntent.putExtra("message", message);
        broadcastIntent.putExtra("name", name);
        Log.d("Message From GCM", message);
        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
        Intent gcmIntent = new Intent(context, CallActivity.class);
        gcmIntent.putExtras(intent.getExtras());
        startWakefulService(context, gcmIntent);
        setResultCode(Activity.RESULT_OK);
    }
}
