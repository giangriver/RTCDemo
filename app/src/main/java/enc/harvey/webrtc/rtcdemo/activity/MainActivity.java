package enc.harvey.webrtc.rtcdemo.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import enc.harvey.webrtc.rtcdemo.R;
import enc.harvey.webrtc.rtcdemo.adapter.ContactRecyclerViewAdapter;
import enc.harvey.webrtc.rtcdemo.gcm.RegistrationIdManager;
import enc.harvey.webrtc.rtcdemo.listener.OnCallingListener;
import enc.harvey.webrtc.rtcdemo.listener.OnUserListInteractionListener;
import enc.harvey.webrtc.rtcdemo.model.User;
import enc.harvey.webrtc.rtcdemo.utils.AppConfig;

public class MainActivity extends Activity implements OnUserListInteractionListener, OnCallingListener {
    private final String TAG = MainActivity.class.getSimpleName();
    private String caller_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RegistrationIdManager registrationIdManager = new RegistrationIdManager(this, AppConfig.SENDER_ID);
        registrationIdManager.registerIfNeeded(new RegistrationIdManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                Log.d(TAG, "Registration Id: " + registrationId);
            }

            @Override
            public void onFailure(String ex) {
                Log.d(TAG, "Registration Id: Register Fail");
            }
        });

        User user1 = new User(1, "harold", "", "cwgC5irpVrE:APA91bFQxXBjHPyTDBHLk2zHBrDoeQ_i0RVfcSS0KmF-_G-Kg06S-j26GmoOHo2ka1-WGfmUxuyS0b8tGKT9BnuByH47UY0QKr0ztoZg703uWVkm1uAQ3_z997HtQZv-QrVFGGQkghou");
        User user2 = new User(2, "river galaxy S3", "", "e0lrKXqo8ec:APA91bFXohgZsv11T_zdKz8dBczJFRspmI7mNowaIjPl-8iwOlPH35y1S5cEbiwh8vcS9uYwVFBmg33u8m1Knkr4Qycabzv3PJHYpqZEIcoYS31jPTp0VbZ4QV_fGxG1CEmZ7nEfHvuz");
        User user3 = new User(3, "test", "", "c4sE4TeWgr0:APA91bHEOY1OabkOpjph2ZYaWbEsV0Mk_GmO_RsfYFZKObB7qOEnru6xyTXULgcwnTUSGE11_LFlNZox0AGHXO5NlHJW3BOA6eLCJ2lOlNk0C5ipc_aaWFvPwi31XO2VkLTulxiM8zKA");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ContactRecyclerViewAdapter(userList, this));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(onNotice, new IntentFilter("GCMMessage"));


    }

    @Override
    public void onClickCallUser(User user) {
        Log.i(getClass().getSimpleName(), user.getUserName());
        openCallActivity(user.getRegistrationId(), true, getApplicationContext());
    }

    @Override
    public void onIncommingCall(String callerId, Context context) {
//        caller_id = callerId;
//        Log.i(TAG, caller_id + ", " + context.toString());
//        openCallActivity(caller_id, false, context);
    }

    @Override
    public void onReceiveJSONObject(JSONObject object) {

    }

    private BroadcastReceiver onNotice =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            caller_id = intent.getStringExtra("caller_id");
            openCallActivity(caller_id, false, context);
            Log.i("TAO LA HAROLD", "SDSDSDSDJSHDJKHSJKDHSJKDHK");
        }
    };

    private void openCallActivity(String caller_id, boolean isCalling, Context context) {
        Intent intent = new Intent(context.getApplicationContext(), CallActivity.class);
        intent.putExtra("caller_id", caller_id);
        intent.putExtra("isCalling", isCalling);
        startActivity(intent);
    }
}
