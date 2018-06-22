package com.gerbugy.account.secure;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.gerbugy.account.R;

public abstract class SecureTimerActivity extends AppCompatActivity implements SecureTimerListener {

    private int mSecondsInFinished = SecureTimerContext.SECONDS_DEFAULT_FINISHED;
    private int mSecondsInWarning = SecureTimerContext.SECONDS_DEFAULT_WARNING;

    private final SecureTimerHandler mHandler = new SecureTimerHandler(this);

    private AlertDialog mTimerDialog;
    private TextView mTimerText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    protected void setSecureTimer(int secondsUntilFinished, int secondsInWarning) {
        mSecondsInFinished = secondsUntilFinished;
        mSecondsInWarning = secondsInWarning;
    }

    @Override
    protected void onStart() {
        super.onStart();
        sendStart();
    }

    @Override
    protected void onStop() {
        mHandler.sendEmptyMessage(SecureTimerContext.MSG_STOP);
        super.onStop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            sendStart();
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        sendStart();
        return super.onKeyDown(keyCode, event);
    }

    private void sendStart() {
        mHandler.sendMessage(Message.obtain(null, SecureTimerContext.MSG_START, mSecondsInFinished, mSecondsInWarning));
    }

    @Override
    public void onSecureCounting(int seconds) {

    }

    @Override
    public void onSecureWarning(int seconds) {
        Log.d("TEST", "onSecureWarning:" + seconds);
        if (mTimerDialog == null) {
            mTimerDialog = new AlertDialog.Builder(this).setView(getLayoutInflater().inflate(R.layout.dialog_text, null)).setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    sendStart();
                }
            }).create();
        }
        if (!mTimerDialog.isShowing()) {
            mTimerDialog.show();
            mTimerText = mTimerDialog.findViewById(R.id.message);
            mTimerText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTimerDialog.cancel();
                }
            });
        }
        mTimerText.setText(getString(R.string.app_until_exit, seconds));
    }

    @Override
    public void onSecureFinished() {
        finishAffinity();
    }
}
