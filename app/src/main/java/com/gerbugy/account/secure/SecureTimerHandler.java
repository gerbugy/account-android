package com.gerbugy.account.secure;

import android.os.Handler;
import android.os.Message;

public class SecureTimerHandler extends Handler implements SecureTimerContext {

    private final SecureTimerListener mListener;

    private SecureTimerRunnable mRunnable;

    public SecureTimerHandler(SecureTimerListener listener) {
        mListener = listener;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_START:
                if (mRunnable != null) {
                    mRunnable.stop();
                }
                mRunnable = new SecureTimerRunnable(msg.arg1, msg.arg2, this);
                mRunnable.start();
                break;
            case MSG_STOP:
                if (mRunnable != null) {
                    mRunnable.stop();
                    mRunnable = null;
                }
                break;
            case MSG_COUNTING:
                mListener.onSecureCounting(msg.arg1);
                break;
            case MSG_WARNING:
                mListener.onSecureWarning(msg.arg1);
                break;
            case MSG_FINISHED:
                mListener.onSecureFinished();
                mRunnable = null;
                break;
            default:
                super.handleMessage(msg);
        }
    }
}