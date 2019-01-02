package edu.cs4730.foregroundservicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import hugo.weaving.DebugLog;

@DebugLog
public class ServiceStatusBroadcastReceiver extends BroadcastReceiver {

    private static final String ACTION_NOTIFY_FOREGROUND_SERVICE = BuildConfig.APPLICATION_ID + ".ACTION_NOTIFY_FOREGROUND_SERVICE";
    private static final String EXTRA_STATUS = "extra_status";
    private final Context context;
    private final Callback callback;

    public ServiceStatusBroadcastReceiver(Context context, Callback callback) {
        this.context = context;
        this.callback = callback;
    }

    public static void broadcast(Context context, boolean started) {
        Intent intent = new Intent(ACTION_NOTIFY_FOREGROUND_SERVICE).putExtra(EXTRA_STATUS, started);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void register(Context context, ServiceStatusBroadcastReceiver receiver) {
        IntentFilter filter = new IntentFilter(ACTION_NOTIFY_FOREGROUND_SERVICE);
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter);
    }

    public static void unregister(Context context, ServiceStatusBroadcastReceiver receiver) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
    }

    private void onStatusUpdated(boolean started) {
        log(started);
        callback.onUpdate(started);
    }

    private void log(boolean started) {
        Toast.makeText(context, "ForegroundService started:" + started, Toast.LENGTH_SHORT).show();
    }

    public boolean started(Intent intent) {
        return intent.getBooleanExtra(EXTRA_STATUS, false);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        onStatusUpdated(started(intent));
    }

    public interface Callback {
        void onUpdate(boolean started);
    }
}
