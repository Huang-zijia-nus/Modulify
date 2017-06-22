package com.example.celia.modulifytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent serviceIntent = new Intent(context, MyNotificationService.class);
        context.startService(serviceIntent);
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
