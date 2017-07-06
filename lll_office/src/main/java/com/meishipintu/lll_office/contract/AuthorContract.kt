package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/7/6.
 *
 * 主要功能：
 */
interface AuthorContract {

    interface IView:BasicView{
        fun onSuccess()
    }

    interface IPresenter{
        fun regist(tel: String, name: String, password: String, vcode: String)

        fun login(account:String,psw:String)
    }
}