package com.tyh.java.lib_base.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/10
 * 描述:
 */
public class IntentUtil {

    public static void startActivity(Context context, Class target){
        startActivity(context,target,null);
    }

    public static void startActivity(Context context,Class target,Bundle bundle){
        ActivityCompat.startActivity(context,new Intent(context,target),bundle);
    }

}
