package enc.harvey.webrtc.rtcdemo.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import enc.harvey.webrtc.rtcdemo.R;
import enc.harvey.webrtc.rtcdemo.adapter.ContactRecyclerViewAdapter;
import enc.harvey.webrtc.rtcdemo.gcm.RegistrationIdManager;
import enc.harvey.webrtc.rtcdemo.listener.OnUserListInteractionListener;
import enc.harvey.webrtc.rtcdemo.model.User;
import enc.harvey.webrtc.rtcdemo.utils.AppConfig;
import enc.harvey.webrtc.rtcdemo.utils.Constants;

public class MainActivity extends Activity implements OnUserListInteractionListener {
    private final String TAG = MainActivity.class.getSimpleName();
    private String myRegId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences prefs = getSharedPreferences(AppConfig.PRE_NAME, MODE_PRIVATE);

        RegistrationIdManager registrationIdManager = new RegistrationIdManager(this, AppConfig.SENDER_ID);
        registrationIdManager.registerIfNeeded(new RegistrationIdManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(Constants.KEY_REG_ID, registrationId);
                editor.apply();
                Log.d(TAG, "Registration Id: " + registrationId);
            }

            @Override
            public void onFailure(String ex) {
                Log.d(TAG, "Registration Id: Register Fail");
            }
        });
        myRegId = prefs.getString(Constants.KEY_REG_ID, null);

        List<User> userList = getExampleUserList();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ContactRecyclerViewAdapter(userList, this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(callingRequestReceiver,
                new IntentFilter(Constants.FILTER_RECEIVE_CALLING_REQUEST));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(callingRequestReceiver);
    }

    @Override
    public void onClickCallUser(User user) {
        openCallActivity(myRegId, user.getRegistrationId());
    }

    private BroadcastReceiver callingRequestReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String caller_id = intent.getStringExtra(Constants.KEY_CALLER_ID);
            openCallActivity(caller_id, myRegId);
        }
    };

    private void openCallActivity(String caller_id, String callee_id) {
        if (caller_id != null && !caller_id.trim().isEmpty() &&
                callee_id != null && !callee_id.trim().isEmpty()) {
            Intent intent = new Intent(this, CallActivity.class);
            intent.putExtra(Constants.KEY_CALLER_ID, caller_id);
            intent.putExtra(Constants.KEY_CALLEE_ID, callee_id);
            startActivity(intent);
        }
    }

    private List<User> getExampleUserList() {
        User user1 = new User(1, "harold", "", "cwgC5irpVrE:APA91bFQxXBjHPyTDBHLk2zHBrDoeQ_i0RVfcSS0KmF-_G-Kg06S-j26GmoOHo2ka1-WGfmUxuyS0b8tGKT9BnuByH47UY0QKr0ztoZg703uWVkm1uAQ3_z997HtQZv-QrVFGGQkghou");
        User user2 = new User(2, "river galaxy S3", "", "e0lrKXqo8ec:APA91bFXohgZsv11T_zdKz8dBczJFRspmI7mNowaIjPl-8iwOlPH35y1S5cEbiwh8vcS9uYwVFBmg33u8m1Knkr4Qycabzv3PJHYpqZEIcoYS31jPTp0VbZ4QV_fGxG1CEmZ7nEfHvuz");
        User user3 = new User(3, "test", "", "eo4VxPqEEW4:APA91bFFDdxUKmvizkOpiGbruAQezLGaQFoWlWYyO9cHyYOQNI4iZnb79e2c_hKItahQ6TxsRtLlOiVxlCOHKbQOJWlZDsJgCE0PYp-N6sFsUTSEjGrcFmUkPRXLJk1yPgAlQVh408v9");
        User user4 = new User(4, "river nexus 4", "", "cDwZ9gVw-x8:APA91bGm1W9BwXWCMstAY0-iKeagrpspjTZJx8wsN670Bj_I2PDxoWAKs3qsc2ds-07R_EykmelVGyJx572VK9fsTpDC0zCagxVSzpz_7lmnFImmmc_CztJDKEl6Kw0aJmGt3-M8zzfd");
        User user5 = new User(5, "river nexus 5", "", "cC90f7WfKJA:APA91bHN8Rd4sB6hMZHvRpcXqG7avN8e1ez0bQmGy770K6_VjaTiSBH2PHvYWUkafJ4WuDiy6eMK6OEVxImdx5__zM4exLdk-S7W-Z13IA_D0fACEPqNQX_6dqYTm6vd-Z5KGQEtiADH");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        return userList;
    }
}
