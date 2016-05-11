package enc.harvey.webrtc.rtcdemo.listener;

import org.json.JSONObject;

/**
 * Created by harold on 11/05/2016.
 */
public interface OnCommandListener {
    void onPeer(JSONObject object);
    void onId(String regId);
}
