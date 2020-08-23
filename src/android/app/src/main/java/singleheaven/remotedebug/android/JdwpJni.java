package singleheaven.remotedebug.android;

public class JdwpJni {
    static {
        System.loadLibrary("jdwp");
    }
    public static native boolean restartJdwp(boolean stop);
}
