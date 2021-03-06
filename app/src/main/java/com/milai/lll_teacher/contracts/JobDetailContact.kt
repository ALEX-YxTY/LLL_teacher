package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.OfficeInfo
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

        fun onOfficeInfoGet(officeInfo:OfficeInfo)

        fun onResumeSendSuccess()

        //获取职位投递状态的回调
        fun onJobDeliver(isDeliver: Boolean)
    }

    interface IPresenter:BasicPresenterImp{

        fun isJobCollect(id: Int, uid: String)

        fun addJobCollect(id: Int, add: Boolean,uid:String)

        fun sendResume(jobId:Int,uid:String,oid:String)

        fun getJobDetail(pid:Int)

        fun getOfficeDetail(oid:String)

        //获取职位投递状态
        fun isJobDeliver(pid: Int, tid: String)

        //浏览职位统计接口
        fun addStatistic(uid: String, pid: Int)
    }
}