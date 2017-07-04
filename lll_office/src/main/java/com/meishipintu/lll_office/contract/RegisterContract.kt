package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.UserInfo
import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/7/3.
 *
 * 功能介绍：
 */
interface RegisterContract {

    interface IView :BasicView{
        fun onVCodeGet(vcode:String)
    }

    interface IPresenter {
        fun getVCode(mobile:String)
    }
}