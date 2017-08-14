package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.DeliverInfo
import com.milai.lll_teacher.presenters.BasicPresenterImp
import com.milai.lll_teacher.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/7/27.
 *
 * 主要功能：
 */
interface InterviewListContract {

    interface IView:BasicViewLoadError{
        fun onDeliverHistoryGet(dataList:List<DeliverInfo>,page:Int)
    }

    interface IPresenter:BasicPresenterImp{
        fun getDeliverHistory(tid: String, type: Int = 1, status: Int = 0,page:Int)
    }
}