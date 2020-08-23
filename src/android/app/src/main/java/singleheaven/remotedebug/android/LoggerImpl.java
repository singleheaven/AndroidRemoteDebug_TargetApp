package singleheaven.remotedebug.android;

import javax.inject.Named;

import jdi.log.Log;

@Named("loggerImpl")
public class LoggerImpl implements Log.ILog {
    @Override
    public void e(String tag, String message, Throwable t) {
        android.util.Log.e(tag, message, t);
    }

    @Override
    public void d(String tag, String s) {
        android.util.Log.d(tag, s);
    }

    @Override
    public void e(String tag, String message) {
        android.util.Log.e(tag, message);
    }
}
