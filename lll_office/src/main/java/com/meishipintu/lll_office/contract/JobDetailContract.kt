package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/7/7.
 *
 * 主要功能：
 */
interface JobDetailContract {
    interface IView:BasicView{
        fun onStatusChanged(statusNew:Int)
    }

    interface IPresenter:BasicPresenterImp{
        fun changeStatus(jobId: String, type: Int)

        fun getOrganizationDetail(uid:String)
    }
}