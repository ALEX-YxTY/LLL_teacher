package com.meishipintu.lll_office.contract

import com.milai.lll_teacher.presenters.BasicPresenter
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/7/3.
 *
 * 功能介绍：
 */
interface RegisterContract {

    interface IView : BasicView {

    }

    interface IPresenter:BasicPresenter{

        fun getVCode()

        fun register()
    }
}