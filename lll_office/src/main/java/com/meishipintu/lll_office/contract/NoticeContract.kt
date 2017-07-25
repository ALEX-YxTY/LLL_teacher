package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.MessageNoticeInfo
import com.meishipintu.lll_office.modles.entities.SysNoticeInfo
import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/7/25.
 *
 * 主要功能：
 */
interface NoticeContract {

    interface IView: BasicView {

        fun onSysNoticeGet(dataList:List<SysNoticeInfo>)

        fun onMessageNoticeGet(dataList:List<MessageNoticeInfo>)
    }

    interface IPresenter:BasicPresenterImp{
        fun getSysNotice(oid:String)

        fun getMessageNotice(oid:String)
    }
}