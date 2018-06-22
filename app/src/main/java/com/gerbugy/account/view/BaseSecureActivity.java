package com.gerbugy.account.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.gerbugy.account.secure.SecureTimerActivity;

public abstract class BaseSecureActivity extends SecureTimerActivity {

    private final BaseActivityDelegate mDelegate = new BaseActivityDelegate(this);

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDelegate.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDelegate.onResume();
    }

    @Override
    protected void onPause() {
        mDelegate.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mDelegate.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}