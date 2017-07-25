package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.presenters.BasicPresenterImp
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/7/12.
 *
 * 主要功能：
 */
interface JobDetailContact {
    interface IView : BasicView {

        fun isJobCollected(isCollected: Boolean)

        fun onJobCollected(isCollected: Boolean)

        fun onJobDetailGet(jobInf:com.milai.lll_teacher.models.entities.JobInfo)

        fun onResumeSendSuccess()
    }

    interface IPresenter:BasicPresenterImp{

        fun isJobCollect(id: Int, uid: String)

        fun addJobCollect(id: Int, add: Boolean,uid:String)

        fun sendResume(jobId:Int,uid:String,oid:String)

        fun getJobDetail(pid:Int)
    }
}