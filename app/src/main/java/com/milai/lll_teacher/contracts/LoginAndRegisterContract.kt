package com.milai.lll_teacher.contracts

import com.milai.lll_teacher.models.entities.UserInfo
import com.milai.lll_teacher.presenters.BasicPresenter
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/6/12.
 *
 * 功能介绍：
 */
interface RegisterContract {

    interface iView: BasicView {

        fun onRegisterSucess(userInfo: UserInfo)

        fun onVCode(vCode: String)

    }

    interface iPresenter : BasicPresenter {

        fun getVCode(tel: String)

        fun register(tel: String, vCode: String, psw: String)

    }
}

