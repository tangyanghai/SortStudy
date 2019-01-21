package com.tyh.java.lib_base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tyh.java.lib_base.util.ToastUtil;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView(view);

    }

    protected abstract int getContentView();
    public abstract void initView(View view);


    public void showToash(String msg){
        if (getActivity() != null) {
            ToastUtil.getInstance().show(getActivity(),msg);
        }
    }

    public void showDebug(String msg){
        if (getActivity() != null) {
            ToastUtil.getInstance().showDebug(getActivity(),msg);
        }
    }

    /**
     * 状态控制器
     * 通过状态码code和参数arg控制
     * @param code  状态码
     * @param arg   数据
     */
    public void pushData(int code,Object arg){

    }
}
