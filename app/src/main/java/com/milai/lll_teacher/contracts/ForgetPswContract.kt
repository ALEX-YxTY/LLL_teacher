package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.presenters.BasicPresenterImp
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/7/20.
 *
 * 主要功能：
 */
interface ForgetPswContract {

    interface IView:BasicView{
        fun onVCodeGet(vcode: String)
    }

    interface IPresenter:BasicPresenterImp{
        fun getVCode(tel: String)
    }
}