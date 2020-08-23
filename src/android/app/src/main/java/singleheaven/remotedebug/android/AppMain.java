package singleheaven.remotedebug.android;

import androidx.multidex.MultiDexApplication;

import ioc.Injector;
import jdi.log.Log;

/**
 * <p>@ProjectName:     NettyChat</p>
 * <p>@ClassName:       NettyChatApp.java</p>
 * <p>@PackageName:     com.freddy.chat</p>
 * <b>
 * <p>@Description:     类描述</p>
 * </b>
 * <p>@author:          FreddyChen</p>
 * <p>@date:            2019/04/07 23:58</p>
 * <p>@email:           chenshichao@outlook.com</p>
 */
public class AppMain extends MultiDexApplication {

    private static AppMain instance;

    public static AppMain sharedInstance() {
        if (instance == null) {
            throw new IllegalStateException("app not init...");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Injector.registerQualifiedClass(Log.ILog.class, LoggerImpl.class);
        instance = this;
    }
}
