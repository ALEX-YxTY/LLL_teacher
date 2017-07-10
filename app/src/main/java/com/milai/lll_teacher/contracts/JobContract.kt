package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.presenters.BasicPresenterImp
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
interface JobContract {

    interface IView : BasicView{

        fun onDateGet(dataList:List<JobInfo>)
    }

    interface IPresenter:BasicPresenterImp {

        //查询和筛选职位方法
        fun doSearch(tj: Boolean = true, area: Int = 0, course: Int = 0, grade: Int = 0, experience: Int = 0)
    }
}