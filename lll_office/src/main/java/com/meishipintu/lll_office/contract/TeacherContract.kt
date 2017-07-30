package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
interface TeacherContract {

    interface IView:BasicView {

        fun onDateGet(dataList:List<TeacherInfo>)
    }

    interface IPresenter : BasicPresenterImp {

        fun doSearch(tj: Int = 0, course: Int = 0, grade: Int = 0, experience: Int = 0, decending: Boolean = false)

    }
}