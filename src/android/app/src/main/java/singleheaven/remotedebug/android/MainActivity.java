package singleheaven.remotedebug.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;

import com.alibaba.fastjson.JSONObject;
import com.freddy.chat.im.MessageType;
import com.freddy.im.protobuf.MessageProtobuf;

import java.util.UUID;

import jdi.impl.NettyImpl;

public class MainActivity extends Activity {
    private static final String TAG = "remote_debug";

    private CheckBox start;

    @SuppressLint("SetTextI18n")
    private class DeviceNettyImpl extends NettyImpl {
        public DeviceNettyImpl(String id, String host, int port) {
            super(id, host, port);
        }

        @Override
        protected boolean isNetworkAvailable() {
            return true;
        }

        @Override
        protected MessageProtobuf.Msg getHandshakeMsg() {
            MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
            MessageProtobuf.Head.Builder headBuilder = builder.getHeadBuilder();
            headBuilder.setMsgId(UUID.randomUUID().toString());
            headBuilder.setMsgType(MessageType.HANDSHAKE.getMsgType());
            headBuilder.setFromId(myId);
            headBuilder.setTimestamp(System.currentTimeMillis());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("side", "device");
            headBuilder.setExtend(jsonObject.toJSONString());
            return builder.build();
        }

        @Override
        protected void onError(String msg) {
        }

        @Override
        protected void onPeerHandshake() {
            JdwpJni.restartJdwp(false);
            runOnUiThread(() -> start.setText("jdwp is started!"));

            debugCommunication.attachToJVM("127.0.0.1:8011");
        }

        @Override
        protected void onPeerGoodbye() {
            debugCommunication.detachFromJVM();

            JdwpJni.restartJdwp(true);
            start.setText("jdwp is stopped!");
        }
    }

    private final NettyImpl debugCommunication = new DeviceNettyImpl("android-27cc6c58-10db-4eee-a77f-6ff24800e56d", "192.168.3.14", 8090);

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button testBtn = findViewById(R.id.test_only);
        testBtn.setOnClickListener(view -> {
            int a = 0;
            int b = 1;
            int c = a + b;
            Log.d(TAG, String.valueOf(c));
        });

        start = findViewById(R.id.start_debug);
        start.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                debugCommunication.start();
            } else {
                debugCommunication.stop();
            }
        });
    }
}