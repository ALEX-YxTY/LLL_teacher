package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.presenters.BasicPresenterImp
import com.milai.lll_teacher.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/7/19.
 *
 * 功能介绍：
 */
interface CollectionContract {

    interface IView : BasicViewLoadError {
        fun onJobCollectionGet(dataList: List<JobInfo>, page: Int)
    }

    interface IPresenter:BasicPresenterImp{

        fun getJobCollection(tid: String,page:Int)
    }
}