package enc.harvey.webrtc.rtcdemo.activity;

import android.app.Activity;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.MediaStream;
import org.webrtc.VideoRenderer;
import org.webrtc.VideoRendererGui;

import enc.harvey.webrtc.rtcdemo.R;
import enc.harvey.webrtc.rtcdemo.listener.OnCallingListener;
import enc.harvey.webrtc.rtcdemo.rtc.PeerConnectionParameters;
import enc.harvey.webrtc.rtcdemo.rtc.WebRtcClient;

public class CallActivity extends Activity implements WebRtcClient.RtcListener, View.OnClickListener, OnCallingListener {
    private final String TAG = CallActivity.class.getSimpleName();

    private static final String VIDEO_CODEC_VP9 = "VP9";
    private static final String AUDIO_CODEC_OPUS = "opus";
    // Local preview screen position before call is connected.
    private static final int LOCAL_X_CONNECTING = 0;
    private static final int LOCAL_Y_CONNECTING = 0;
    private static final int LOCAL_WIDTH_CONNECTING = 100;
    private static final int LOCAL_HEIGHT_CONNECTING = 100;
    // Local preview screen position after call is connected.
    private static final int LOCAL_X_CONNECTED = 72;
    private static final int LOCAL_Y_CONNECTED = 72;
    private static final int LOCAL_WIDTH_CONNECTED = 25;
    private static final int LOCAL_HEIGHT_CONNECTED = 25;
    // Remote video screen position
    private static final int REMOTE_X = 0;
    private static final int REMOTE_Y = 0;
    private static final int REMOTE_WIDTH = 100;
    private static final int REMOTE_HEIGHT = 100;
    private VideoRendererGui.ScalingType scalingType = VideoRendererGui.ScalingType.SCALE_ASPECT_FILL;
    private GLSurfaceView vsv;
    private VideoRenderer.Callbacks localRender;
    private VideoRenderer.Callbacks remoteRender;
    private WebRtcClient client;
    private String mCallerId;
    private WebRtcClient.RtcListener rtcListener;

    private String regId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_call);

        regId = getIntent().getStringExtra("caller_id");
        final boolean isCalling = getIntent().getBooleanExtra("isCalling", true);

        vsv = (GLSurfaceView) findViewById(R.id.glview_call);
        vsv.setPreserveEGLContextOnPause(true);
        vsv.setKeepScreenOn(true);

        VideoRendererGui.setView(vsv, new Runnable() {
            @Override
            public void run() {
                init(regId, isCalling);
            }
        });

        // local and remote render
        remoteRender = VideoRendererGui.create(
                REMOTE_X, REMOTE_Y,
                REMOTE_WIDTH, REMOTE_HEIGHT, scalingType, false);
        localRender = VideoRendererGui.create(
                LOCAL_X_CONNECTING, LOCAL_Y_CONNECTING,
                LOCAL_WIDTH_CONNECTING, LOCAL_HEIGHT_CONNECTING, scalingType, true);

//        final Intent intent = getIntent();
//        final String action = intent.getAction();
//        Log.d(TAG, "action: " + action);
//
//        if (Intent.ACTION_VIEW.equals(action)) {
//            final List<String> segments = intent.getData().getPathSegments();
//            callerId = segments.get(0);
//        }
    }

    private void init(String regId, boolean isCalling) {
        Point displaySize = new Point();
        Log.i("Point", displaySize.toString());
        getWindowManager().getDefaultDisplay().getSize(displaySize);
        PeerConnectionParameters params = new PeerConnectionParameters(
                true, false, displaySize.x, displaySize.y, 30, 1, VIDEO_CODEC_VP9, true, 1, AUDIO_CODEC_OPUS, true);

        client = new WebRtcClient(this, params, VideoRendererGui.getEGLContext());
        if (isCalling) {
            this.onCallReady(regId, true);
        } else {
            this.onCallReady(regId, false);
        }


    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        switch (v.getId()) {
            case R.id.btAnswer:
                break;
            case R.id.btEndCall:
                finish();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        vsv.onResume();
        if (client != null) {
            client.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        vsv.onPause();
        if (client != null) {
            client.onPause();
        }
    }

    @Override
    public void onDestroy() {
        if (client != null) {
            client.onDestroy();
        }
        super.onDestroy();
    }

    public void answer(String callerId) throws JSONException {
        client.sendMessage(callerId, "init", null);
        startCam(callerId);
    }

    public void call(String callId) {
//        Intent msg = new Intent(Intent.ACTION_SEND);
//        msg.putExtra(Intent.EXTRA_TEXT, mSocketAddress + callId);
//        msg.setType("text/plain");
//        startActivityForResult(Intent.createChooser(msg, "Call someone :"), VIDEO_CALL_SENT);
        startCam(callId);
    }

    public void startCam(String callId) {
        // Camera settings
        Log.i("Registration Id ", callId);
        client.start(callId, "android_test");
    }

    @Override
    public void onCallReady(String callId, boolean isCalling) {
        if (isCalling) {
            Log.d(TAG, "Calling");
            call(callId);
        } else {
            try {
                Log.d(TAG, "Answering");
                answer(callId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStatusChanged(final String newStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), newStatus, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onLocalStream(MediaStream localStream) {
        Log.d(TAG, "==============================================================================");
        Log.d(TAG, "onLocalStream");
        localStream.videoTracks.get(0).addRenderer(new VideoRenderer(localRender));
        VideoRendererGui.update(localRender,
                LOCAL_X_CONNECTING, LOCAL_Y_CONNECTING,
                LOCAL_WIDTH_CONNECTING, LOCAL_HEIGHT_CONNECTING,
                scalingType);
    }

    @Override
    public void onAddRemoteStream(MediaStream remoteStream, int endPoint) {
        Log.d(TAG, "onAddRemoteStream");
        remoteStream.videoTracks.get(0).addRenderer(new VideoRenderer(remoteRender));
        VideoRendererGui.update(remoteRender,
                REMOTE_X, REMOTE_Y,
                REMOTE_WIDTH, REMOTE_HEIGHT, scalingType);
        VideoRendererGui.update(localRender,
                LOCAL_X_CONNECTED, LOCAL_Y_CONNECTED,
                LOCAL_WIDTH_CONNECTED, LOCAL_HEIGHT_CONNECTED,
                scalingType);
    }

    @Override
    public void onRemoveRemoteStream(int endPoint) {
        Log.d(TAG, "onRemoveRemoteStream");
        VideoRendererGui.update(localRender,
                LOCAL_X_CONNECTING, LOCAL_Y_CONNECTING,
                LOCAL_WIDTH_CONNECTING, LOCAL_HEIGHT_CONNECTING,
                scalingType);
    }


    @Override
    public void onIncommingCall(final String callerId) {
    }

    @Override
    public void onReceiveJSONObject(JSONObject object) {
//        client.callMsgHandlerOnPeer(object);
    }
}
