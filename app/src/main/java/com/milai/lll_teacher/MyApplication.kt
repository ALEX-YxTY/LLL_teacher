package com.milai.lll_teacher

import android.app.Application
import com.milai.lll_teacher.models.entities.UserInfo
import com.milai.lll_teacher.models.https.HttpApiClinet
import com.milai.lll_teacher.models.https.HttpResultFunc
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Administrator on 2017/6/12.
 *
 *
 * 功能介绍：application 类
 */

class MyApplication : Application() {

    companion object {
        var userInfo: UserInfo? = null
        var instance:MyApplication?=null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        userInfo = Cookies.getUserInfo()
        downloadResource()
    }

    private fun downloadResource() {
        val httpApi = HttpApiClinet.retrofit()
        for (type: Int in 1..6) {
            httpApi.getConstantArraysService(type).map(HttpResultFunc<Array<String>>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        result ->
                        Cookies.saveConstant(type, result)
                    })
        }
    }

}
