package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.UserInfo
import com.milai.lll_teacher.presenters.BasicPresenterImp
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/8/8.
 *
 * 主要功能：
 */
interface MineContract {
    interface IView : BasicView {
        fun onUserInfoGet(userInfo: UserInfo)
    }

    interface IPresenter:BasicPresenterImp{
        fun getUserInfo(uid: String)
    }
}