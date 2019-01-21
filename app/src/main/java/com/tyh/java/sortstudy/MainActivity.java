package com.tyh.java.sortstudy;

import android.os.Bundle;

import com.tyh.java.lib_base.BaseActivity;
import com.tyh.java.lib_base.util.IntentUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.bt_to_sort_activity)
    public void onViewClicked() {
        IntentUtil.startActivity(this,SortActivity.class);
    }
}
