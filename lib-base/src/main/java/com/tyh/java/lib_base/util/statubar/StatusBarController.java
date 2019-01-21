package com.tyh.java.lib_base.util.statubar;

import android.app.Activity;

import com.jaeger.library.StatusBarUtil;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/18
 * 描述:状态栏控制器-->分离状态栏工具组件
 */
class StatusBarController {

    public static void setColor(Activity activity,int colorRes) {
        StatusBarUtil.setColor(activity,activity.getResources().getColor(colorRes));
    }
}
