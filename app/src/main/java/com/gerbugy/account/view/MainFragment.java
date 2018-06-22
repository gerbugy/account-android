package com.gerbugy.account.view;

import android.view.View;

import com.gerbugy.account.view.BaseFragment;

public abstract class MainFragment extends BaseFragment {

    private boolean mHasFloatingActionButton;

    public void onFloatingActionButtonClick(View v) {

    }

    protected void setHasFloatingActionButton(boolean hasFloatingActionButton) {
        mHasFloatingActionButton = hasFloatingActionButton;
    }

    public boolean hasFloatingActionButton() {
        return mHasFloatingActionButton;
    }
}
