package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.presenters.BasicPresenterImp
import com.milai.lll_teacher.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/6/23.
 *
 * 主要功能：
 */
interface OfficeContract {

    interface IView : BasicViewLoadError {
        fun onDataGet(dataList: List<OfficeInfo>,page:Int)
    }

    interface IPresenter :BasicPresenterImp{
        fun getOffice(page: Int = 1)

        fun searchOfficeByKeyword(keyword: String, page: Int)
    }
}