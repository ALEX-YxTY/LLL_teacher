package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.presenters.BasicPresenterImp
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/7/11.
 *
 * 主要功能：
 */
interface OfficeDetailContract{
    interface IView:BasicView{
        fun onPositionGet(data: List<JobInfo>)
    }

    interface IPresenter:BasicPresenterImp{
        fun getOrganizationPosition(uid: String)
    }
}