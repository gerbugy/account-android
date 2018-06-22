package com.gerbugy.account.secure;

import android.os.Message;

public class SecureTimerRunnable implements Runnable {

    private final int mSecondsInFinished;
    private final int mSecondsInWarning;
    private final SecureTimerHandler mHandler;

    private boolean mCancelled;

    public SecureTimerRunnable(int secondsUntilFinished, int secondsInWarning, SecureTimerHandler handler) {
        mSecondsInFinished = secondsUntilFinished;
        mSecondsInWarning = secondsInWarning;
        mHandler = handler;
    }

    public synchronized void start() {
        new Thread(this).start();
    }

    public synchronized void stop() {
        mCancelled = true;
    }

    @Override
    public void run() {
        for (int seconds = mSecondsInFinished; seconds > 0; seconds--) {
            if (mCancelled) {
                return;
            }
            mHandler.sendMessage(Message.obtain(null, SecureTimerContext.MSG_COUNTING, seconds, 0));
            if (seconds <= mSecondsInWarning) {
                mHandler.sendMessage(Message.obtain(null, SecureTimerContext.MSG_WARNING, seconds, 0));
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mHandler.sendEmptyMessage(SecureTimerContext.MSG_FINISHED);
    }
}
