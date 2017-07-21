package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.UserInfo
import com.milai.lll_teacher.presenters.BasicPresenterImp
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/7/20.
 *
 * 主要功能：
 */
interface ResetPswContract {

    interface IView:BasicView{
        fun onReSetSuccsee()
    }

    interface IPresenter:BasicPresenterImp{
        fun resetPsw(tel: String, vcode: String, psw: String)
    }
}