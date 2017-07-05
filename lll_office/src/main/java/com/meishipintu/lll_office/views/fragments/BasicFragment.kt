package com.meishipintu.lll_office.views.fragments

import android.support.v4.app.Fragment
import android.widget.Toast

/**
 * Created by Administrator on 2017/7/5.
 *
 * 主要功能：
 */
open class BasicFragment: Fragment() {

    fun toast(msg: String, time: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this.activity,msg,time).show()
    }
}