package edu.cs4730.foregroundservicedemo;

import android.app.Application;

import hugo.weaving.DebugLog;
import io.github.android.tang.tony.file.logger.FileLogger;

@DebugLog
public class App extends Application {
    private DemoServiceStatusTracker helper;

    @Override
    public void onCreate() {
        super.onCreate();
        FileLogger.init(this);
        helper = new DemoServiceStatusTracker(this);
    }

    public DemoServiceStatusTracker getHelper() {
        return helper;
    }
}
