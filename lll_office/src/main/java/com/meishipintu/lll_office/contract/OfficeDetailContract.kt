package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/7/11.
 *
 * 主要功能：
 */
interface OfficeDetailContract{

    interface IView: BasicView {
        fun onPositionGet(data: List<JobInfo>)

    }

    interface IPresenter: BasicPresenterImp {

        fun getOrganizationPosition(uid: String)
    }
}