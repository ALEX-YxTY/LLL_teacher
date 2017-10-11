package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/7/6.
 *
 * 功能介绍：
 */
interface JobManagerContract {

    interface IView:BasicView{
        fun onDateGet(dataList:List<JobInfo>)

        fun onInviteSuccess()
    }

    interface IPresenter:BasicPresenterImp{
        fun getDataList(uid: String, status: Int)

        //邀请面试
        fun inviteInterview(jobId:Int,tid:String,oid:String)
    }
}