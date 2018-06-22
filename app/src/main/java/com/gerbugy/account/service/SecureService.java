package com.gerbugy.account.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class SecureService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TEST", "onStartCommand");
        new Thread(new CountDownRunnable()).start();
        return super.onStartCommand(intent, flags, startId);
    }

    private class CountDownRunnable implements Runnable {

        @Override
        public void run() {
            Log.d("TEST", "CountDownRunnable");
        }
    }
}
