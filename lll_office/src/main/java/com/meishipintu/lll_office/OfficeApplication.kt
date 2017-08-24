package com.meishipintu.lll_office

import android.app.Application
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.UserInfo
import com.tencent.bugly.Bugly
import com.tencent.bugly.crashreport.CrashReport
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
        //initBugly
        Bugly.init(this, "79549921d1", true)
        downloadResource()
    }

    private fun downloadResource() {
        val httpApi = HttpApiClinet.retrofit()
        for (type: Int in 1..11) {
            if (type == 11) {
                Cookies.saveConstant(11, arrayOf("全年级", "一年级", "二年级", "三年级", "四年级", "五年级", "六年级"))
            } else {
                httpApi.getConstantArraysService(type).map(HttpResultFunc())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            result ->
                            Cookies.saveConstant(type, result)
                        })
            }
        }
        httpApi.getMemberDesc().map(HttpResultFunc())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result ->
                    //type=12 会员描述
                    Cookies.saveConstant(12, result)
                })
    }

}