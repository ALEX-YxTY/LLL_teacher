package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.MessageNoticeInfo
import com.meishipintu.lll_office.modles.entities.SysNoticeInfo
import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/7/25.
 *
 * 主要功能：
 */
interface NoticeContract {

    interface IView: BasicViewLoadError {

        fun onSysNoticeGet(dataList: List<SysNoticeInfo>, page: Int)

        fun onMessageNoticeGet(dataList:List<MessageNoticeInfo>,page:Int)
    }

    interface IPresenter:BasicPresenterImp{

        fun getSysNotice(oid:String,page:Int)

        fun getMessageNotice(oid:String,page:Int)
    }
}