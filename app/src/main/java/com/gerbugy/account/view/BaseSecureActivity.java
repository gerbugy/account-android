package com.gerbugy.account.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

import com.gerbugy.account.service.SecureService;
import com.gerbugy.account.service.SecureTimer;

public abstract class BaseSecureActivity extends BaseActivity {

    private SecureTimer mSecureTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE); // 최근앱 스크린샷을 방지합니다.

    }

    @Override
    protected void onStart() {
        super.onStart();
        startSecureService();
        // mSecureTimer.bind(this); // 주석처리 시 A->B->A로 돌아온 경우 A와 연결되지 못합니다.
        // mSecureTimer.reset(); // 주석해제 시 최근앱목록에서 클릭하면 카운터가 초기화됩니다.
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        startSecureService();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            startSecureService();
        }
        return super.dispatchTouchEvent(ev);
    }

    private void startSecureService() {
        startService(new Intent(this, SecureService.class));
    }

    private AlertDialog mTimerDialog;
    private TextView mTimerText;

    public void showWarning(int secondsUntilFinished) {
//        if (isStarted()) {
//            if (mTimerDialog == null) {
//                mTimerDialog = new AlertDialog.Builder(this).setView(getLayoutInflater().inflate(R.layout.dialog_text, null)).setOnCancelListener(new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialogInterface) {
//                        mSecureManager.restart();
//                    }
//                }).create();
//            }
//            if (!mTimerDialog.isShowing()) {
//                mTimerDialog.show();
//                mTimerText = mTimerDialog.findViewById(R.id.message);
//                mTimerText.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mTimerDialog.cancel();
//                    }
//                });
//            }
//            mTimerText.setText(getString(R.string.auto_exit, secondsUntilFinished));
//        }
    }
}
