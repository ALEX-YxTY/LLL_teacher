package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.ChatDetail
import com.meishipintu.lll_office.modles.entities.OfficeInfo
import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/7/13.
 *
 * 主要功能：
 */
interface ChatDetailContract {
    interface IView: BasicView {
        fun onOfficeInfoGet(officeInfo: OfficeInfo)

        fun onChaListGet(dataList: List<ChatDetail>)

        fun onSendChatSuccess()
    }

    interface IPresenter: BasicPresenterImp {
        fun getOfficeInfo(oid: String)

        fun getChatList(tid: String, pid: Int)

        fun sendChat(pid: Int, tid: String, oid: String, content: String)
    }
}