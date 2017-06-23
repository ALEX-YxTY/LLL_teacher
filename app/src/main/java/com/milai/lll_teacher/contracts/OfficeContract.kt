package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.presenters.BasicPresenter
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/6/23.
 *
 * 主要功能：
 */
interface OfficeContract {

    interface IView : BasicView {
        fun onDataGet(dataList: List<OfficeInfo>)
    }

    interface IPresenter:BasicPresenter{
        fun getOffice()
    }
}