package enc.harvey.webrtc.rtcdemo.gcm;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import enc.harvey.webrtc.rtcdemo.utils.AppConfig;

/**
 * Created by harold on 11/05/2016.
 */
public class MessageSender {

    private HttpURLConnection connection;

    public void sendPost() {
        try {
            URL gcmAPI = new URL(AppConfig.GCM_API);
            connection = (HttpURLConnection) gcmAPI.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "key=" + AppConfig.API_KEY);
            connection.setDoOutput(true);

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
