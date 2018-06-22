package com.gerbugy.account;

import android.app.Application;

import com.gerbugy.account.db.SQLiteHelper;
import com.gerbugy.account.util.AppUtils;

public final class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 데이터베이스를 초기화합니다.
        SQLiteHelper.initialize(this, Constants.DATABASE_NAME, null, AppUtils.getPackageInfo(this).versionCode);

        // 데이터베이스를 생성 또는 업그레이드 합니다.
        SQLiteHelper.getInstance().getReadableDatabase();
    }
}