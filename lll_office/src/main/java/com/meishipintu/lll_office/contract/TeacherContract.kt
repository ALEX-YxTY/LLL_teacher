package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
interface TeacherContract {

    interface IView:BasicViewLoadError {

        fun onDateGet(dataList:List<TeacherInfo>,page:Int)
    }

    interface IPresenter : BasicPresenterImp {

        fun doSearch(tj: Int = 0, experience: Int = 0, course: Int = 0, grade: Int = 0, decending: Int = 0, page: Int = 1)

        fun searchTeacher(keyWord:String,page:Int)
    }
}