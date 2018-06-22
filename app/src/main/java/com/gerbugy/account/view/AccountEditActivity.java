package com.gerbugy.account.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.gerbugy.account.Constants;
import com.gerbugy.account.R;
import com.gerbugy.account.databinding.AccountEditBinding;
import com.gerbugy.account.db.AccountDao;
import com.gerbugy.account.util.SQLiteItem;

public class AccountEditActivity extends BaseSecureActivity implements View.OnClickListener {

    private SQLiteItem mItem;
    private AccountEditBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.account_edit);
        mBinding.okButton.setOnClickListener(this);
        long _id = getIntent().getLongExtra(AccountDao.Columns._ID, Constants.NO_ID);
        if (_id > Constants.NO_ID) {
            select(_id);
            setTitle(R.string.account_edit);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_button:
                if (mItem == null) {
                    insert();
                } else {
                    update();
                }
                break;
        }
    }

    private void select(long _id) {
        mItem = AccountDao.getInstance().select(_id);
        mBinding.name.setText(mItem.getString(AccountDao.Columns.NAME));
        mBinding.name.setSelection(mBinding.name.getText().length());
        mBinding.loginId.setText(mItem.getString(AccountDao.Columns.LOGIN_ID));
        mBinding.loginPassword.setText(mItem.getString(AccountDao.Columns.LOGIN_PASSWORD));
        mBinding.url.setText(mItem.getString(AccountDao.Columns.URL));
        mBinding.description.setText(mItem.getString(AccountDao.Columns.DESCRIPTION));
    }

    private void insert() {
        mItem = new SQLiteItem();
        mItem.put(AccountDao.Columns.NAME, mBinding.name.getText().toString().trim());
        mItem.put(AccountDao.Columns.LOGIN_ID, mBinding.loginId.getText().toString().trim());
        mItem.put(AccountDao.Columns.LOGIN_PASSWORD, mBinding.loginPassword.getText().toString().trim());
        mItem.put(AccountDao.Columns.URL, mBinding.url.getText().toString().trim());
        mItem.put(AccountDao.Columns.DESCRIPTION, mBinding.description.getText().toString().trim());
        setResult(Constants.RESULT_INSERTED, new Intent().putExtra(AccountDao.Columns._ID, AccountDao.getInstance().insert(mItem)));
        finish();
    }

    private void update() {
        mItem.put(AccountDao.Columns.NAME, mBinding.name.getText().toString().trim());
        mItem.put(AccountDao.Columns.LOGIN_ID, mBinding.loginId.getText().toString().trim());
        mItem.put(AccountDao.Columns.LOGIN_PASSWORD, mBinding.loginPassword.getText().toString().trim());
        mItem.put(AccountDao.Columns.URL, mBinding.url.getText().toString().trim());
        mItem.put(AccountDao.Columns.DESCRIPTION, mBinding.description.getText().toString().trim());
        AccountDao.getInstance().update(mItem);
        setResult(Constants.RESULT_CHANGED, new Intent().putExtra(AccountDao.Columns._ID, mItem.getLong(AccountDao.Columns._ID)));
        finish();
    }
}
