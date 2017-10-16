package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicInviteView
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/7/19.
 *
 * 主要功能：
 */
interface TeacherDetailContract {

    interface IView:BasicInviteView{
        fun onIsCollected(isCollected: Boolean)

        //收藏回调，isCollect=true 收藏成功，false，取消收藏成功
        fun collectSuccess(isCollect:Boolean)

        fun onTeacherInfoGet(teacherInfo: TeacherInfo)
    }

    interface IPresenter:BasicPresenterImp{

        fun isCollectedTeacher(oid: String, tid: String)

        fun collectTeacher(oid: String, tid: String)

        fun uncollectTeacher(oid:String,tid:String)

        //邀请面试
        fun inviteInterview(jobId:Int,tid:String,oid:String)

        //添加tongji
        fun doActionStatistic(uid: String, tid: String)

        //获取教师详情
        fun getTeacherInfo(tid: String)
    }
}