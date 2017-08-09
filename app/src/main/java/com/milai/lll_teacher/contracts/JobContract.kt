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

        //loadMore参数用来区分加载更多和普通筛选
        fun onDateGet(dataList: List<JobInfo>, loadMore: Boolean)
    }

    interface IPresenter:BasicPresenterImp {

        //查询和筛选职位方法
        //loadMore参数用来区分加载更多和普通筛选
        fun doSearch(tj: Int = 1, area: Int = 0, course: Int = 0, grade: Int = 0, experience: Int = 0
                     , page: Int = 1, loadMore: Boolean = false)

        //根据关键字搜索职位
        fun getJobByKeyWord(keyword:String)
    }
}