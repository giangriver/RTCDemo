package enc.harvey.webrtc.rtcdemo.gcm;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import enc.harvey.webrtc.rtcdemo.utils.AppConfig;

/**
 * Created by harold on 11/05/2016.
 */
public class MessageSender {

    public void sendPost(List<String> to, JSONObject message) {
        /**
         * registration_ids
         * data
         */
        JSONObject msg = new JSONObject();
        try {
            msg.put("registration_ids", to);
            msg.put("data", message);

            URL gcmAPI = new URL(AppConfig.GCM_API);
            HttpURLConnection connection = (HttpURLConnection) gcmAPI.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "key=" + AppConfig.API_KEY);
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(msg.toString().getBytes());
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                Log.i("Request Status", "This is success response status from server: " + responseCode);
            } else {
                Log.i("Request Status", "This is failure response status from server: " + responseCode);
            }


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
