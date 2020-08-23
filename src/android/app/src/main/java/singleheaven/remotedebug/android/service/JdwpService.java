package singleheaven.remotedebug.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.IOException;

import jdi.jdwp.SocketTransportWrapper;
import jdi.jdwp.TransportWrapper;

public class JdwpService extends Service {
    private TransportWrapper transportWrapper;
    public JdwpService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        transportWrapper = new SocketTransportWrapper();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        transportWrapper = new SocketTransportWrapper();
        try {
            transportWrapper.attach("127.0.0.1:8011", 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return START_NOT_STICKY;
    }
}
