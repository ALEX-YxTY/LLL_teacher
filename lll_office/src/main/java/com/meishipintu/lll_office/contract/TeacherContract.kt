package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.TeacherInfo

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
interface TeacherContract {

    interface IView {

        fun onDateGet(dataList:List<TeacherInfo>)
    }

    interface IPresenter{

        fun doSearch(tj: Int = 0, year: Int = 0, workYear: Int = 0, education: Int = 0, decending: Boolean = false)

    }
}