package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.DeliverInfo
import com.milai.lll_teacher.presenters.BasicPresenterImp
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/7/27.
 *
 * 主要功能：
 */
interface InterviewListContract {

    interface IView:BasicView{
        fun onDeliverHistoryGet(dataList:List<DeliverInfo>)
    }

    interface IPresenter:BasicPresenterImp{
        fun getDeliverHistory(tid: String, type: Int = 1, status: Int = 0)
    }
}