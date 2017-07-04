package com.meishipintu.lll_office.views

import android.support.v7.app.AppCompatActivity
import android.widget.Toast


/**
 * Created by Administrator on 2017/7/4.
 *
 * 主要功能：
 */
open class BasicActivity:AppCompatActivity() {

    fun toast(msg: String, time: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this,msg,time).show()
    }
}