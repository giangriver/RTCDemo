package enc.harvey.webrtc.rtcdemo.gcm;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import enc.harvey.webrtc.rtcdemo.listener.OnCallingListener;

/**
 * Created by harold on 10/05/2016.
 */
public class MessageReceiver extends WakefulBroadcastReceiver {

    private OnCallingListener listener;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        String strData = bundle.getString("data");
        JSONObject data = null;
        try {
            data = new JSONObject(strData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Set<String> keys = bundle.keySet();
//        for (String key : keys) {
//            try {
//                data.put(key, JSONObject.wrap(bundle.get(key)));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        listener.onIncommingCall(data);

    }
}
