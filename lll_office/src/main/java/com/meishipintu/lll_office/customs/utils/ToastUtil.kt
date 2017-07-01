package com.meishipintu.lll_office.customs.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by Administrator on 2017/7/1.
 *
 * 功能介绍：
 */
object ToastUtil {
    fun show(ctx: Context, content: String, isShort: Boolean) {
        Toast.makeText(ctx, content, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
    }
}