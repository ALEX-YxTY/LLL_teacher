package com.milai.lll_teacher.custom.util;

import android.content.Context;

/**
 * Created by Administrator on 2017/1/20.
 */

public class DensityUtils {

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */
    public static int dip2px(Context context,  float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
//        Toast.makeText(context,"density:"+scale,Toast.LENGTH_SHORT).show();
        return (int) (dpValue * scale );
    }

    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     * */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale );
    }
}
