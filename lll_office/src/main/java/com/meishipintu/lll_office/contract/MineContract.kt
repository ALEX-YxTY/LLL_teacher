package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.UserInfo
import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/8/18.
 *
 * 主要功能：
 */
interface MineContract {
    interface IView:BasicView{
        fun onUserInfoGet(userInfo:UserInfo)
    }

    interface IPresenter : BasicPresenterImp {
        fun getUserInfo(uid: String)
    }
}