package enc.harvey.webrtc.rtcdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends Activity implements OnUserListInteractionListener {
    private final String TAG = MainActivity.class.getSimpleName();

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

        User user1 = new User(1, "harold","", "cwgC5irpVrE:APA91bFQxXBjHPyTDBHLk2zHBrDoeQ_i0RVfcSS0KmF-_G-Kg06S-j26GmoOHo2ka1-WGfmUxuyS0b8tGKT9BnuByH47UY0QKr0ztoZg703uWVkm1uAQ3_z997HtQZv-QrVFGGQkghou");
        User user2 = new User(2, "river galaxy S3","", "e0lrKXqo8ec:APA91bFXohgZsv11T_zdKz8dBczJFRspmI7mNowaIjPl-8iwOlPH35y1S5cEbiwh8vcS9uYwVFBmg33u8m1Knkr4Qycabzv3PJHYpqZEIcoYS31jPTp0VbZ4QV_fGxG1CEmZ7nEfHvuz");
        User user3 = new User(3, "test","", "");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ContactRecyclerViewAdapter(userList, this));

    }

    @Override
    public void onClickCallUser(User user) {
        Log.i(getClass().getSimpleName(), user.getUserName());
        Intent intent = new Intent(this, CallActivity.class);
        intent.putExtra("regId", user.getRegistrationId());
        intent.putExtra("username", user.getUserName());
        startActivity(intent);
    }
}
