package com.meishipintu.lll_office.modles

import android.util.Log
import com.meishipintu.lll_office.modles.entities.HttpResult
import io.reactivex.functions.Function

/**
 * Created by Administrator on 2017/7/4.
 *
 * 主要功能：
 */
class HttpResultFunc<T>:Function<HttpResult<T>,T> {
    override fun apply(t: HttpResult<T>): T {
        Log.d("http", t.toString())
        if (t != null ) {
            if (t.status != 1) {
                throw RuntimeException(t.msg)
            } else {
                return t.data
            }
        } else {
            throw RuntimeException("获取网络信息失败")
        }
    }
}