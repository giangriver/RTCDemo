package enc.harvey.webrtc.rtcdemo.gcm;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
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
            msg.put("data", message.toString())              ;

            URL gcmAPI = new URL(AppConfig.GCM_API);
            HttpURLConnection connection = (HttpURLConnection) gcmAPI.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "key=" + AppConfig.API_KEY);
            connection.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(msg.toString());
            wr.close();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
