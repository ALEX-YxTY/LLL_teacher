package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.models.entities.OfficeInfo
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

        fun isOfficeCollected(isCollect: Boolean)

        fun onOfficeCollectResult(isAdd: Boolean)

        fun onOfficeCollectionGet(data:List<OfficeInfo>)
    }

    interface IPresenter:BasicPresenterImp{

        fun getOrganizationPosition(uid: String)

        fun isOfficeCollect(officeId: String, teacherId: String)

        fun addOrganizationCollection(officeId: String, teacherId: String, isAddded: Boolean)

        fun getOrganizationCollection(teacherId:String)
    }
}