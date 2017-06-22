package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.presenters.BasicPresenter
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

    interface IPresenter:BasicPresenter{

        fun doSearch(tj: Int = 0, area: Int = 0, year: Int = 0, workYear: Int = 0, education: Int = 0)
    }
}