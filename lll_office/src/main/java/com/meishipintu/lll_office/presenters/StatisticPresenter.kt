package com.meishipintu.lll_office.presenters

import com.meishipintu.lll_office.contract.ActionStatisticContract
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpApiStores
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.entities.HttpResult

/**
 * Created by Administrator on 2017/8/18.
 *
 * 主要功能：
 */
class StatisticPresenter : BasicPresenter(), ActionStatisticContract.IPresenter {

    val httpApi:HttpApiStores by lazy { HttpApiClinet.retrofit() }
    override fun doNewsStatistic(uid:String, type:Int, newsId: String) {
        addSubscription(httpApi.doActionSattistic(uid,2,type,newsId),object:HttpCallback<HttpResult<Any>>(){
            override fun onSuccess(model: HttpResult<Any>) {
            }

            override fun onFailure(msg: String?) {
            }

        })
    }

}