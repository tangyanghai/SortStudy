package com.tyh.java.lib_base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tyh.java.lib_base.util.ToastUtil;
import com.tyh.java.lib_base.util.statubar.ColorStatusBar;
import com.tyh.java.lib_base.util.statubar.IStatusBar;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private IStatusBar statusBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        initStatusBar();
        initData();
    }

    private void initStatusBar() {
        statusBar = setStatusBar();
        if (statusBar != null) {
            statusBar.show();
        }
    }

    protected IStatusBar setStatusBar() {
        return new ColorStatusBar(this, R.color.colorPrimary);
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    public void showToash(String msg) {
        ToastUtil.getInstance().show(this, msg);
    }

    public void showDebug(String msg) {
        ToastUtil.getInstance().showDebug(this, msg);
    }

    /**
     * 状态控制器
     * 通过状态码code和参数arg控制
     *
     * @param code 状态码
     * @param arg  数据
     */
    public void pushData(int code, Object arg) {

    }
}
