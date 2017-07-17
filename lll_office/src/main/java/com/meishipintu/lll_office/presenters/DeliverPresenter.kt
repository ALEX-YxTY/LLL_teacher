package com.meishipintu.lll_office.presenters

import com.meishipintu.lll_office.contract.MyInterviewContract
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.DeliverInfo
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/7/17.
 *
 * 主要功能：
 */
class DeliverPresenter(val iView:BasicView):BasicPresenter(), MyInterviewContract.IPresenter {

    override fun getDeliverHistory(uid: String, status: Int, type: Int) {
        addSubscription(HttpApiClinet.retrofit().getDeliverHistoryService(uid,status,type)
                .map(HttpResultFunc<List<DeliverInfo>>()),object :HttpCallback<List<DeliverInfo>>(){
            override fun onSuccess(model: List<DeliverInfo>) {
                (iView as MyInterviewContract.IView).onDeleverDataGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }

        })
    }
}