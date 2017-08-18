package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.MessageNoticeInfo
import com.milai.lll_teacher.models.entities.SysNoticeInfo
import com.milai.lll_teacher.models.entities.VersionInfo
import com.milai.lll_teacher.presenters.BasicPresenterImp
import com.milai.lll_teacher.views.BasicView
import com.milai.lll_teacher.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/7/25.
 *
 * 主要功能：
 */
interface NoticeContract {

    interface IView: BasicViewLoadError {

        fun onSysNoticeGet(dataList:List<SysNoticeInfo>, page: Int)

        fun onMessageNoticeGet(dataList:List<MessageNoticeInfo>, page: Int)

        fun onNewestMessIdGet(id: Int)

        fun onNewestSysIdGet(id:Int)

        fun onVersionGet(versionInfo:VersionInfo)

    }

    interface IPresenter: BasicPresenterImp {
        fun getNewestSysId(tid: String)

        fun getNewestMessId(tid: String)

        fun getSysNotice(tid: String, page: Int)

        fun getMessageNotice(tid:String, page: Int)

        fun getNewsetVersiton()
    }
}