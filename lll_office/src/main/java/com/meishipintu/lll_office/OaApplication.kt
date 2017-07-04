package com.meishipintu.lll_office

import android.app.Application
import android.util.Log


import com.meishipintu.lll_office.modles.entities.UserInfo

import org.json.JSONObject

import java.util.Arrays
import java.util.HashMap


/**
 * Created by Administrator on 2017/3/1.
 */

class OaApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this

    }

    companion object {

        var instance: OaApplication? = null
        var user: UserInfo? = null
    }

}
