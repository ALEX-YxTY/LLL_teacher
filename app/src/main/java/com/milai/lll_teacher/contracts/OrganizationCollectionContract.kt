package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.presenters.BasicPresenterImp
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/7/19.
 *
 * 功能介绍：
 */
interface OrganizationCollectionContract {

    interface IView : BasicView {
        fun onOrganizationCollectionGet(dataList: List<OfficeInfo>)
    }

    interface IPresenter:BasicPresenterImp{

        fun getOrganizationCollection(tid: String)
    }
}