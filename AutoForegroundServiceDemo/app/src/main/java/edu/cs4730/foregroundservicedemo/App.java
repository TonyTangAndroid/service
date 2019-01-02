package edu.cs4730.foregroundservicedemo;

import android.app.Application;

import hugo.weaving.DebugLog;

@DebugLog
public class App extends Application {
    private DemoServiceStatusTracker helper;

    @Override
    public void onCreate() {
        super.onCreate();
        helper = new DemoServiceStatusTracker(this);
    }

    public DemoServiceStatusTracker getHelper() {
        return helper;
    }
}
