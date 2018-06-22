package com.gerbugy.account.view;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.gerbugy.account.R;
import com.gerbugy.account.util.AdUtils;
import com.gerbugy.account.widget.FixAppBarLayoutBehavior;
import com.google.android.gms.ads.AdView;

final class BaseActivityDelegate {

    private final AppCompatActivity mActivity;

    BaseActivityDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void onPostCreate(Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            mActivity.setSupportActionBar(toolbar);
        }
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (toolbar != null) {
            DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawerLayout != null) {
                ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(mActivity, drawerLayout, toolbar, R.string.open_drawer_content_desc, R.string.close_drawer_content_desc);
                drawerLayout.addDrawerListener(drawerToggle);
                drawerToggle.syncState();
            }
        }
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        if (appBarLayout != null) {
            ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).setBehavior(new FixAppBarLayoutBehavior()); // 첫번째 클릭이 정상적으로 수행되지 않는 현상을 방지합니다. (참고: https://gist.github.com/chrisbanes/8391b5adb9ee42180893300850ed02f2)
        }
        AdView adView = (AdView) findViewById(R.id.ad_view);
        if (adView != null) {
            AdUtils.loadAd(adView);
        }
    }

    public void onResume() {
        AdView adView = (AdView) findViewById(R.id.ad_view);
        if (adView != null) {
            adView.resume();
        }
    }

    public void onPause() {
        AdView adView = (AdView) findViewById(R.id.ad_view);
        if (adView != null) {
            adView.pause();
        }
    }

    public void onDestroy() {
        AdView adView = (AdView) findViewById(R.id.ad_view);
        if (adView != null) {
            adView.destroy();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mActivity.onBackPressed();
                return true;
        }
        return false;
    }

    private View findViewById(int id) {
        return mActivity.findViewById(id);
    }
}
