package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.milai.lll_teacher.presenters.BasicPresenter
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
interface TeacherContract {

    interface IView : BasicView {

        fun onDateGet(dataList:List<TeacherInfo>)
    }

    interface IPresenter: BasicPresenter {

        fun doSearch(tj: Int = 0, year: Int = 0, workYear: Int = 0, education: Int = 0, decending: Boolean = false)

    }
}