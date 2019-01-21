package com.tyh.java.lib_base.util.statubar;

import android.app.Activity;
import android.support.annotation.ColorRes;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/18
 * 描述:
 */
public class ColorStatusBar implements IStatusBar {
    Activity activity;
    @ColorRes
    int color;

    public ColorStatusBar(Activity activity, int color) {
        this.activity = activity;
        this.color = color;
    }

    @Override
    public void show() {
        StatusBarController.setColor(activity,color);
    }
}
