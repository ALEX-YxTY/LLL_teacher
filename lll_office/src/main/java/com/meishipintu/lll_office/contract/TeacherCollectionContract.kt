package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/7/19.
 *
 * 主要功能：
 */
interface TeacherCollectionContract {

    interface IView:BasicViewLoadError{
        fun onTeacherCollectionGet(dataList: List<TeacherInfo>,page:Int)
    }

    interface IPresenter:BasicPresenterImp{
        fun getTeacherCollectiion(oid:String,page:Int)
    }
}