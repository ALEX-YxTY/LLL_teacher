package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.UserInfo
import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicView
import java.io.File

/**
 * Created by Administrator on 2017/7/28.
 *
 * 主要功能：
 */
interface UpdateInfoContract {

    interface IView:BasicView{
        fun onUploadSuccess(userInfo:UserInfo)
    }

    interface IPresenter:BasicPresenterImp{
        fun updateOfficeInfo(uid:String, name: String, address: String, contact: String, contactTel: String
                             , introduce: String, certificate: File)
    }
}