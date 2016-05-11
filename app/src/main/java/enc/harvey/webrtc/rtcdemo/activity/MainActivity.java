package enc.harvey.webrtc.rtcdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity implements OnUserListInteractionListener {

    private List<User> userList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RegistrationIdManager registrationIdManager = new RegistrationIdManager(this, AppConfig.SENDER_ID);
        registrationIdManager.registerIfNeeded(new RegistrationIdManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                Log.i("Registration Id", registrationId);
            }

            @Override
            public void onFailure(String ex) {
                Log.i("Registration Id", "Register Fail");
            }
        });

        User user1 = new User(1, "harold","", "");
        User user2 = new User(2, "river","", "");
        User user3 = new User(3, "test","", "");
        userList = new ArrayList<>();
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
        startActivity(intent);
    }
}
