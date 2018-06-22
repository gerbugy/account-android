package com.gerbugy.account.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.gerbugy.account.R;
import com.gerbugy.account.databinding.PinBinding;

public class PinEditActivity extends BaseActivity {

    private PinBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.pin);
    }
}