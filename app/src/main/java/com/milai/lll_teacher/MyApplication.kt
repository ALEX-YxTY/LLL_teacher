package com.milai.lll_teacher

import android.app.Application
import com.milai.lll_teacher.models.entities.UserInfo
import com.milai.lll_teacher.models.https.HttpApiClinet
import com.milai.lll_teacher.models.https.HttpResultFunc
import com.tencent.bugly.Bugly
import com.tencent.bugly.crashreport.CrashReport
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.nio.charset.Charset

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
        //initBugly
        val strategy = CrashReport.UserStrategy(instance)
        strategy.setCrashHandleCallback(object :CrashReport.CrashHandleCallback() {
            override fun onCrashHandleStart(crashType: Int, errorType: String?, errorMessage: String?
                                            , errorStack: String?): MutableMap<String, String> {
                val map = LinkedHashMap<String, String>()
                val x5CrashInfo = com.tencent.smtt.sdk.WebView.getCrashExtraMessage(instance)
                map.put("x5crashInfo", x5CrashInfo)
                return map
            }

            override fun onCrashHandleStart2GetExtraDatas(crashType: Int, errorType: String?, errorMessage: String?
                                                          , errorStack: String?): ByteArray? {
                try {
                    return "Extra data.".toByteArray(Charsets.UTF_8)
                } catch (e: Exception) {
                    return null
                }
            }
        })
        Bugly.init(this, "6cd126f554", true, strategy)
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
