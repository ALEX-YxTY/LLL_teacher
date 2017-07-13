package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.ChatDetail
import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.presenters.BasicPresenterImp
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/7/13.
 *
 * 主要功能：
 */
interface ChatDetailContract {
    interface IView:BasicView{
        fun onOfficeInfoGet(officeInfo: OfficeInfo)

        fun onChaListGet(dataList: List<ChatDetail>)

        fun onSendChatSuccess()
    }

    interface IPresenter:BasicPresenterImp{
        fun getOfficeInfo(oid: String)

        fun getChatList(tid: String, pid: Int)

        fun sendChat(pid: Int, tid: String, oid: String, content: String)
    }
}