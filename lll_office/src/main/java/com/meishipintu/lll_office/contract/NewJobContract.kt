package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/7/6.
 *
 * 主要功能：
 */
interface NewJobContract {

    interface IView:BasicView{
        fun onJobAddSucess()
    }

    interface IPresenter:BasicPresenterImp{
        fun addJob(name: String, oid: String, workArea: Int, wordAddress: String, course: Int, grade: Int
                   , sex: Int, requireYear: Int, money: String, certificate: Int, require: String, other: String)

    }
}