package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/8/9.
 *
 * 主要功能：
 */
interface NoticeActivityContract {

    interface IView:BasicView{
        fun onNewestMessIdGet(id: Int)

        fun onNewestSysIdGet(id:Int)
    }

    interface IPresenter:BasicPresenterImp{
        fun getNewestSysId(tid: String)

        fun getNewestMessId(tid: String)
    }
}