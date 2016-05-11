package enc.harvey.webrtc.rtcdemo.gcm;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import enc.harvey.webrtc.rtcdemo.utils.AppConfig;

/**
 * Created by harold on 11/05/2016.
 */
public class MessageSender {

    public void sendPost(JSONObject message) {
        try {
            URL gcmAPI = new URL(AppConfig.GCM_API);
            HttpURLConnection connection = (HttpURLConnection) gcmAPI.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "key=" + AppConfig.API_KEY);
            connection.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(message.toString());
            wr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
