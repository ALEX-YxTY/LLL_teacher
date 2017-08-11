package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.presenters.BasicPresenterImp
import com.milai.lll_teacher.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/8/1.
 *
 * 主要功能：
 */
interface SearchContract {

    interface IView: BasicViewLoadError {
        fun onJobGet(dataList: List<JobInfo>, page: Int)

        fun onOfficeGet(dataList: List<OfficeInfo>, page: Int)
    }

    interface IPresenter:BasicPresenterImp{
        fun getJobByCourse(courseIndex: Int, page: Int)
    }
}