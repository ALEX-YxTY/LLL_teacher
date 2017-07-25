package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.MessageNoticeInfo
import com.milai.lll_teacher.models.entities.SysNoticeInfo
import com.milai.lll_teacher.presenters.BasicPresenterImp
import com.milai.lll_teacher.views.BasicView

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

    interface IPresenter: BasicPresenterImp {
        fun getSysNotice(tid:String)

        fun getMessageNotice(tid:String)
    }
}