package com.meishipintu.lll_office.contract

/**
 * Created by Administrator on 2017/7/7.
 *
 * 主要功能：
 */
interface JobDetailContract {
    interface IView{
        fun onStatusChanged(statusNew:Int)
    }

    interface IPresenter{
        fun changeStatus(jobId: String, type: Int)
    }
}