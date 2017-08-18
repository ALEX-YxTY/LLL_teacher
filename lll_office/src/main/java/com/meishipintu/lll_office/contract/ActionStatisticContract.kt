package com.meishipintu.lll_office.contract

import com.meishipintu.lll_office.modles.entities.AdInfo
import com.meishipintu.lll_office.modles.entities.NewsInfo
import com.meishipintu.lll_office.presenters.BasicPresenterImp
import com.meishipintu.lll_office.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/8/1.
 *
 * 主要功能：
 */
interface ActionStatisticContract {

    interface IPresenter:BasicPresenterImp{
        fun doNewsStatistic(uid:String, tyep:Int, newsId:String)
    }
}