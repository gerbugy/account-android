package com.gerbugy.account.service;

import android.os.CountDownTimer;
import android.util.Log;

import com.gerbugy.account.view.BaseSecureActivity;

public final class SecureTimer extends CountDownTimer {

    private static final long MILLIS_IN_FUTURE = 1000 * 60; // 1분동안 인터랙션이 없으면 종료합니다.
    private static final long MILLIS_IN_ALERT = 1000 * 30; // 30초동안 경고창을 보여줍니다.

    private static SecureTimer sInstance;

    private BaseSecureActivity mActivity;

    private SecureTimer() {
        super(MILLIS_IN_FUTURE, 1000);
    }

    public static SecureTimer getInstance() {
        synchronized (SecureTimer.class) {
            if (sInstance == null) {
                sInstance = new SecureTimer();
            }
        }
        return sInstance;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        Log.d("TEST", "onTick:" + millisUntilFinished);
        if (millisUntilFinished < MILLIS_IN_ALERT + 1000) {
            // mActivity.onAlertFinish((int) Math.ceil(millisUntilFinished / 1000));
        }
    }

    @Override
    public void onFinish() {
        Log.d("TEST", "onFinish");
        if (mActivity != null) {
            mActivity.finishAffinity();
        }
    }

    public void reset222() {
        cancel();
        start();
    }

    public void bind(BaseSecureActivity activity) {
        mActivity = activity;
    }
}
