package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/8/31.
 *
 * 主要功能：
 */
interface ReSetPswContract {

    interface IView:BasicView{
        fun onReSetSuccsee()
    }

    interface IPresenter:BasicPresenterImp{
        fun resetPsw(tel: String, vcode: String, psw: String)
    }
}