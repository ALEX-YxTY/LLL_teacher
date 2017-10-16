package com.meishipintu.lll_office

import android.app.Application
import android.util.Log
import cn.jpush.android.api.JPushInterface
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.UserInfo
import com.tencent.bugly.Bugly
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.UMShareAPI


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
        PlatformConfig.setWeixin("wxe5ad8590f4758f52", "fbfd75214fe1485807ccbae265f25e79")

        instance = this
        userInfo = Cookies.getUserInfo()
        //initBugly
        Bugly.init(this, "79549921d1", true)
        downloadResource()

        //init JPUsh
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)

        //分享
        UMShareAPI.get(this)
    }

    private fun downloadResource() {
        val httpApi = HttpApiClinet.retrofit()
        for (type: Int in 1..10) {
            httpApi.getConstantArraysService(type).map(HttpResultFunc())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        result ->
                        Cookies.saveConstant(type, result)
                    })
        }
        Cookies.saveConstant(11, arrayOf("全年级", "一年级", "二年级", "三年级", "四年级", "五年级", "六年级"))

        httpApi.getMemberDesc().map(HttpResultFunc())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result ->
                    //type=12 会员描述
                    Cookies.saveConstant(12, result)
                },{
                    err ->
                    Log.d("test", err.message)
                })
    }

}