package com.gerbugy.account.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.gerbugy.account.R;
import com.gerbugy.account.util.AppUtils;

public class MainDrawerListener implements DrawerLayout.DrawerListener {

    private final MainActivity mActivity;

    MainDrawerListener(MainActivity activity) {
        mActivity = activity;
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        DrawerLayout drawerLayout = mActivity.getBinding().drawerLayout;
        Object tag = drawerLayout.getTag();
        if (tag != null) {
            switch (Integer.parseInt(tag.toString())) {
                case R.id.database:
                    startActivity(new Intent(mActivity, DatabaseActivity.class));
                    break;
                case R.id.memo:
                    AppUtils.launchOrMarket(mActivity, mActivity.getString(R.string.memo_package_name));
                    break;
            }
            drawerLayout.setTag(null);
        }
    }

    private void startActivity(Intent intent) {
        mActivity.startActivity(intent);
    }
}