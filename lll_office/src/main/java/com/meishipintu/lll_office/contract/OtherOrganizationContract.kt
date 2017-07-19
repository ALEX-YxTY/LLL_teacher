package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.OfficeInfo
import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/7/19.
 *
 * 主要功能：
 */
interface OtherOrganizationContract {
    interface IView:BasicView{
        fun onOrganizationGet(dataList:List<OfficeInfo>)
    }

    interface IPresenter:BasicPresenterImp{
        fun getOrganization(oid:String)
    }
}