package com.milai.lll_teacher.views.fragments

import android.support.v4.app.Fragment
import android.widget.Toast
import com.milai.lll_teacher.presenters.BasicPresenterImp

/**
 * Created by Administrator on 2017/7/5.
 *
 * 主要功能：
 */
open class BasicFragment: Fragment() {

    var presenter: BasicPresenterImp? = null

    fun toast(msg: String, time: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this.activity,msg,time).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter?.unsubscribe()
        }
    }
}