package com.meishipintu.lll_office.modles

import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException

/**
 * Created by Administrator on 2017/7/4.
 *
 * 主要功能：
 */
abstract class HttpCallback<T>:Observer<T> {
    abstract fun onSuccess(model:T)
    abstract fun onFailure(msg:String?)

    fun onFinish(){

    }

    override fun onNext(m:T) {
        onSuccess(m)
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onError(e: Throwable) {
        //这块应该可以优化
        if (e is HttpException) {
            val httpException = e
            //httpException.response().errorBody().string()
            val code = httpException.code()
            var msg = httpException.message
            Log.d("httpException", "code=" + code)
            if (code == 504) {
                msg = "网络不给力"
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试"
            }
            onFailure(msg)
        } else {
            onFailure(e.toString())
        }
        onFinish()
    }

    override fun onComplete() {
        onFinish()
    }

}