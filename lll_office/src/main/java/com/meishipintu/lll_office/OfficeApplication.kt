package com.meishipintu.lll_office

import android.app.Application
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.UserInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Administrator on 2017/7/4.
 *
 * 主要功能：
 */
class OfficeApplication :Application() {
    companion object {
        var userInfo: UserInfo? = null
        var instance:OfficeApplication?=null
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