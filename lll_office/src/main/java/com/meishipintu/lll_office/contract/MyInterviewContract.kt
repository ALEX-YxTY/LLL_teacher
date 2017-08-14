package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.DeliverInfo
import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/7/14.
 *
 * 主要功能：
 */
interface MyInterviewContract {

    interface IView:BasicViewLoadError{
        fun onDeleverDataGet(dataList: List<DeliverInfo>,page:Int)
    }

    interface IPresenter:BasicPresenterImp{
        fun getDeliverHistory(uid: String, status: Int = 0, type: Int = 0, page: Int = 1)
    }
}