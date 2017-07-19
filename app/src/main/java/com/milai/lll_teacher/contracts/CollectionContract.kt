package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.presenters.BasicPresenterImp
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/7/19.
 *
 * 功能介绍：
 */
interface CollectionContract {

    interface IView : BasicView {
        fun onJobCollectionGet(dataList: List<JobInfo>)
    }

    interface IPresenter:BasicPresenterImp{

        fun getJobCollection(tid: String)
    }
}