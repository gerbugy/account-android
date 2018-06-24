package com.gerbugy.account.secure;

import android.os.Message;

import com.gerbugy.account.secure.SecureActivity.SecureHandler;

class SecureTimer {

    private static final int SECONDS_UNTIL_FINISHED = 60;
    private static final int SECONDS_IN_WARNING = 30;

    private static SecureTimer sInstance;

    private TimerRunnable mRunnable;
    private long mRunnableId;

    private SecureTimer() {

    }

    static {
        sInstance = new SecureTimer();
    }

    static long start(SecureHandler handler) {
        return sInstance.cancelAndStart(handler);
    }

    private synchronized long cancelAndStart(SecureHandler handler) {
        if (mRunnable != null) {
            mRunnable.cancel();
        }
        mRunnable = new TimerRunnable(handler);
        mRunnable.start();
        return ++mRunnableId;
    }

    public static long getRunningId() {
        return sInstance.mRunnableId;
    }

    private class TimerRunnable implements Runnable {

        final SecureHandler mHandler;

        boolean mCancelled;

        TimerRunnable(SecureHandler handler) {
            mHandler = handler;
        }

        void start() {
            new Thread(this).start();
        }

        void cancel() {
            mCancelled = true;
        }

        @Override
        public void run() {
            for (int seconds = SECONDS_UNTIL_FINISHED; seconds > 0; seconds--) {
                if (mCancelled) {
                    return;
                }
                if (seconds <= SECONDS_IN_WARNING) {
                    mHandler.sendMessage(Message.obtain(null, SecureHandler.WARNING, seconds, 0));
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mHandler.sendEmptyMessage(SecureHandler.FINISHED);
        }
    }
}