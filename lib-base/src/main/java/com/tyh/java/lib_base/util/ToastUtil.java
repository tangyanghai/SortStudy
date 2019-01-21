package com.tyh.java.lib_base.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.widget.Toast;

public class ToastUtil {
    private Toast mToast;
    private Context mContext;

    private static final ToastUtil ourInstance = new ToastUtil();

    public static ToastUtil getInstance() {
        return ourInstance;
    }

    private ToastUtil() {

    }

    public void showLong(Context context, String msg) {
        showWithDuration(context, msg, Toast.LENGTH_LONG, false);
    }

    public void show(Context context, String msg) {
        showWithDuration(context, msg, Toast.LENGTH_SHORT, false);
    }

    public void show(Context context, String msg, int duration) {
        showWithDuration(context, msg, duration, false);
    }

    public void showLongDebug(Context context, String msg) {
        showWithDuration(context, msg, Toast.LENGTH_LONG, true);
    }

    public void showDebug(Context context, String msg) {
        showWithDuration(context, msg, Toast.LENGTH_SHORT, true);
    }

    public void showDebug(Context context, String msg, int duration) {
        showWithDuration(context, msg, duration, true);
    }


    private synchronized void showWithDuration(Context context, String msg, int duration, boolean showDebug) {
        if (context == null) {
            return;
        }

        if (mContext == null) {
            mContext = context.getApplicationContext();
        }

        if (showDebug) {
            if (!isApkInDebug(mContext)) {
                return;
            }
        }

        if (mToast == null) {
            mToast = Toast.makeText(mContext, msg, duration);
        } else {
            mToast.setDuration(duration);
            mToast.setText(msg);
        }

        mToast.show();
    }


    private boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

}
