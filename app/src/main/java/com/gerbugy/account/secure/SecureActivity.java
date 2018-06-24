package com.gerbugy.account.secure;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.gerbugy.account.R;

public abstract class SecureActivity extends AppCompatActivity {

    private static final String TIMER_ID = "timer_id";

    private final SecureHandler mHandler = new SecureHandler(this);

    private AlertDialog mTimerDialog;
    private TextView mTimerText;
    private long mTimerId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mTimerId == 0 || mTimerId != SecureTimer.getRunningId()) {
            sendStartMessage();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            sendStartMessage();
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        sendStartMessage();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(TIMER_ID, mTimerId);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTimerId = savedInstanceState.getLong(TIMER_ID);
    }

    protected void onSecureWarning(int seconds) {
        if (mTimerDialog == null) {
            mTimerDialog = new AlertDialog.Builder(this)
                    .setView(getLayoutInflater().inflate(R.layout.dialog_text, null))
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            sendStartMessage();
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

    protected void onSecureFinished() {
        finishAffinity();
    }

    private void sendStartMessage() {
        mHandler.sendEmptyMessage(SecureHandler.START);
    }

    private void onSecureStarted(long timerId) {
        mTimerId = timerId;
    }

    static class SecureHandler extends Handler {

        static final int START = 0;
        static final int WARNING = 1;
        static final int FINISHED = 2;

        private final SecureActivity mActivity;

        SecureHandler(SecureActivity activity) {
            mActivity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START:
                    mActivity.onSecureStarted(SecureTimer.start(this));
                    break;
                case WARNING:
                    mActivity.onSecureWarning(msg.arg1);
                    break;
                case FINISHED:
                    mActivity.onSecureFinished();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
