package com.meishipintu.lll_office.presenters

import com.meishipintu.lll_office.contract.InterviewContract
import com.meishipintu.lll_office.contract.MyInterviewContract
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.DeliverInfo
import com.meishipintu.lll_office.views.BasicView
import com.meishipintu.lll_office.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/7/17.
 *
 * 主要功能：
 */
class DeliverPresenter(val iView:BasicView):BasicPresenter(), MyInterviewContract.IPresenter
        ,InterviewContract.IPresenter{

    val httpApi = HttpApiClinet.retrofit()

    //获取机构被投递记录
    override fun getDeliverHistory(uid: String, status: Int, type: Int,page:Int) {
        addSubscription(httpApi.getDeliverHistoryService(uid,status,type,page)
                .map(HttpResultFunc<List<DeliverInfo>>()),object :HttpCallback<List<DeliverInfo>>(){
            override fun onSuccess(model: List<DeliverInfo>) {
                (iView as MyInterviewContract.IView).onDeleverDataGet(model,page)
            }

            override fun onFailure(msg: String?) {
                (iView as BasicViewLoadError).onLoadError()
                if (msg != null) {
                    iView.onError(msg)
                }
            }

        })
    }

    override fun updateDeliverStatus(deliverId: Int, status: Int, score: Int, evaluate: String) {
        addSubscription(httpApi.updateDeliverStatusService(deliverId,status,score,evaluate).map(HttpResultFunc<DeliverInfo>())
                ,object :HttpCallback<DeliverInfo>(){

            override fun onSuccess(model: DeliverInfo) {
                (iView as InterviewContract.IView).onStatusUpdateSuccess(model.status)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }

        })

    }
}