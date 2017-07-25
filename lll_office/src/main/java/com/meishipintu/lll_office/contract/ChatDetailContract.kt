package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.ChatDetail
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/7/13.
 *
 * 主要功能：
 */
interface ChatDetailContract {
    interface IView: BasicView {
        fun onTeacherInfoGet(teacherInfo: TeacherInfo)

        fun onJobInfoGet(jobInfo:JobInfo)

        fun onChaListGet(dataList: List<ChatDetail>)

        fun onSendChatSuccess()
    }

    interface IPresenter: BasicPresenterImp {
        fun getTeacherInfo(tid: String)

        fun getJobInfo(pid:Int)

        fun getChatList(tid: String, pid: Int)

        fun sendChat(pid: Int, tid: String, oid: String, content: String)
    }
}