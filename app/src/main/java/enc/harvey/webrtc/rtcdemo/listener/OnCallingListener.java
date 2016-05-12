package enc.harvey.webrtc.rtcdemo.listener;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by harold on 11/05/2016.
 */
public interface OnCallingListener {
    void onIncommingCall(String callerId, Context context);
    void onReceiveJSONObject(JSONObject object);
}
