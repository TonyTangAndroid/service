package edu.cs4730.foregroundservicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import hugo.weaving.DebugLog;

@DebugLog
public class DeviceRebootedBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            onDeviceRebooted(context);
        } else {
            throw new RuntimeException("Unsupported action : " + intent.getAction());
        }
    }

    private void onDeviceRebooted(Context context) {
        Intent serviceToBeStarted = DemoService.constructDemoService(context);
        if (supportToStartForegroundService()) {
            startServiceDirectly(context, serviceToBeStarted);
        } else {
            startServiceThroughJobScheduler(context, serviceToBeStarted);
        }
    }

    private void startServiceThroughJobScheduler(Context context, Intent serviceToBeStarted) {
        DeviceRebootedJobSchedulerService.enqueueWork(context, serviceToBeStarted);
    }

    private void startServiceDirectly(Context context, Intent serviceToBeStarted) {
        context.startService(serviceToBeStarted);
    }

    private boolean supportToStartForegroundService() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.O;
    }
}