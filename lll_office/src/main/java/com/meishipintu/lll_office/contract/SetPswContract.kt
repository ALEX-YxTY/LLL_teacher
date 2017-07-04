package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.UserInfo
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/7/4.
 *
 * 主要功能：
 */
interface SetPswContract {

    interface IView:BasicView{
        fun onRegisterSuccsee(userInfo:UserInfo)
    }

    interface IPresenter{
        fun regist(tel: String, name: String, password: String, vcode: String)
    }
}