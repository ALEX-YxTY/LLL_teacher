package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.Message
import com.milai.lll_teacher.models.entities.SysNotice
import com.milai.lll_teacher.presenters.BasicPresenter
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/6/23.
 *
 * 主要功能：
 */
interface NoticeContract {

    interface IView :BasicView{
        fun onMessageGet(messages: List<Message>)

        fun onNoticeGet(notices:List<SysNotice>)
    }

    interface IPresenter:BasicPresenter{
        fun getMessage()

        fun getNotice()
    }
}